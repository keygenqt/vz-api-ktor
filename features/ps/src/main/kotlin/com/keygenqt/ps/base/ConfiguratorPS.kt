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
import com.keygenqt.core.base.LoaderConfig
import com.keygenqt.core.base.Password
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.ps.components.github.GitHubService
import com.keygenqt.ps.db.models.UserEntity
import com.keygenqt.ps.db.models.Users
import com.keygenqt.ps.route.articles.articlesRoute
import com.keygenqt.ps.route.auth.authRoute
import com.keygenqt.ps.route.dashboard.dashboardRoute
import com.keygenqt.ps.route.projects.projectsRoute
import com.keygenqt.ps.route.upload.fileRoute
import com.keygenqt.ps.service.*
import com.keygenqt.ps.utils.Constants
import com.keygenqt.ps.utils.Constants.APP_CONFIG
import com.keygenqt.ps.utils.Constants.BASE_API_PATH
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.math.BigInteger
import java.security.MessageDigest

class ConfiguratorPS : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql
    private lateinit var appConf: LoaderConfig
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
                single { UploadsService(db) }

                // http client
                single {
                    GitHubService(HttpClient(CIO) {
                        install(ContentNegotiation) {
                            json(Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                            })
                        }
                        expectSuccess = true
                    })
                }
            }
        )
    }

    override fun Route.routing() {
        "/${BASE_API_PATH}".let { basePath ->
            route(basePath) {
                authRoute()
                fileRoute()
                dashboardRoute() // @todo
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
            appConf = LoaderConfig.loadProperties(this.property("${APP_CONFIG}.properties").getString())

            // init db app
            db = DatabaseMysql(
                config = property("${APP_CONFIG}.config").getString(),
                migration = property("${APP_CONFIG}.migration").getString()
            )

            // init securityService
            securityService = SecurityService(db, appConf.getPropOrNull(Constants.Properties.secret)!!)

            // update admin password
            appConf.getPropOrNull<Boolean?>(Constants.Properties.adminUpdate)?.let {
                if (it) {
                    val email: String = appConf.getPropOrNull(Constants.Properties.adminEmail)!!
                    runBlocking {
                        db.transaction {
                            Password.random().let { pass ->
                                UserEntity.find { (Users.email eq email) }.firstOrNull()?.let { entity ->
                                    entity.password = Password.encode(pass)
                                }
                                println("Update ADMIN, email: $email, password: $pass")
                                pass
                            }
                        }
                    }
                }
            }
        }
    }

    override fun SessionsConfig.session() {

        val secretHex = appConf.getPropOrNull<String>(Constants.Properties.secret)!!.toByteArray(Charsets.UTF_8)
            .let { MessageDigest.getInstance("MD5").digest(it) }
            .let { String.format("%032x", BigInteger(1, it)) }
            .let { hex(it) }

        val signKey = appConf.getPropOrNull<String>(Constants.Properties.signKey)!!.toByteArray(Charsets.UTF_8)
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
