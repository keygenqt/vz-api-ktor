/*
 * Copyright 2022 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.ps.base

import com.keygenqt.core.base.ConfiguratorApp
import com.keygenqt.core.base.LoaderConfig.loadProperties
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.ps.route.articles.articlesRoute
import com.keygenqt.ps.route.auth.authRoute
import com.keygenqt.ps.route.projects.projectsRoute
import com.keygenqt.ps.service.*
import com.keygenqt.ps.utils.Constants.APP_CONFIG
import com.keygenqt.ps.utils.Constants.BASE_API_PATH
import com.keygenqt.ps.utils.Constants.DBCONFIG_CONFIG
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class ConfiguratorPS : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql
    private lateinit var properties: Properties
    private lateinit var securityService: SecurityService

    private val jwtAuth = "jwtAuth"
    private val sessionAuth = "sessionAuth"

    override fun di(): Array<Module> {
        return arrayOf(
            module {
                // security routing service
                single { securityService }

                // api db models services
                single { UsersService(db) }
                single { TokensService(db) }
                single { ArticlesService(db) }
                single { ProjectsService(db) }
            }
        )
    }

    override fun Route.routing() {
        "/${BASE_API_PATH}".let { basePath ->
            route(basePath) {
                authRoute()
            }
            authenticate(jwtAuth, sessionAuth) {
                route(basePath) {
                    articlesRoute()
                    projectsRoute()
                }
            }
        }
    }

    override fun Application.configure() {
        with(environment.config) {
            // auth secret
            properties = loadProperties(this.property("${APP_CONFIG}.config").getString())

            // init db app
            db = DatabaseMysql(
                config = property("${DBCONFIG_CONFIG}.config").getString(),
                migration = property("${DBCONFIG_CONFIG}.migration").getString()
            )

            // init securityService
            securityService = SecurityService(db, properties.getProperty("secret").toString())
        }
    }

    override fun SessionsConfig.session() {

        val secretHex = properties.getProperty("secret").toString().toByteArray(Charsets.UTF_8)
            .let { MessageDigest.getInstance("MD5").digest(it) }
            .let { String.format("%032x", BigInteger(1, it)) }
            .let { hex(it) }

        val signKey = properties.getProperty("signKey").toString().toByteArray(Charsets.UTF_8)
            .let { MessageDigest.getInstance("MD5").digest(it) }
            .let { String.format("%032x", BigInteger(1, it)) }
            .let { hex(it) }

        cookie<UserSession>("ps_session") {
            transform(SessionTransportTransformerEncrypt(secretHex, signKey))
        }
    }

    override fun AuthenticationConfig.authentication() {
        jwt(jwtAuth) {
            realm = "Access to Personal Site"
            verifier(securityService.verifier)
            validate { securityService.findUserByCredential(it) }
        }
        session<UserSession>(sessionAuth) {
            validate { session ->
                try {
                    securityService.verify(session.token)?.let {
                        securityService.findUserByID(it)
                    } ?: run {
                        null
                    }
                } catch (ex: Exception) {
                    println(ex)
                    null
                }
            }
        }
    }
}
