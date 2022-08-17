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
import com.keygenqt.ps.route.articlesRoute
import com.keygenqt.ps.route.auth.authRoute
import com.keygenqt.ps.route.projectsRoute
import com.keygenqt.ps.route.usersRoute
import com.keygenqt.ps.service.*
import com.keygenqt.ps.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*

class ConfiguratorPS : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql
    private lateinit var jwtProperties: Properties
    private lateinit var securityService: SecurityService

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
        "/${Constants.BASE_API_PATH}".let { basePath ->
            route(basePath) {
                authRoute()
            }
            authenticate(this@ConfiguratorPS::class.simpleName) {
                route(basePath) {
                    articlesRoute()
                    projectsRoute()
                    usersRoute()
                }
            }
        }
    }

    override fun Application.configure() {
        with(environment.config) {
            // auth secret
            jwtProperties =
                loadProperties(this.property("${Constants.JWT_CONFIG}.config").getString())

            // init db app
            db = DatabaseMysql(
                config = property("${Constants.DBCONFIG_CONFIG}.config").getString(),
                migration = property("${Constants.DBCONFIG_CONFIG}.migration").getString()
            )

            // init securityService
            securityService = SecurityService(db, jwtProperties.getProperty("secret").toString())
        }
    }


    override fun AuthenticationConfig.authentication() {
        jwt(this@ConfiguratorPS::class.simpleName) {
            realm = jwtProperties.getProperty("realm").toString()
            verifier(securityService.verifier)
            validate { securityService.findUserByCredential(it) }
        }
    }
}
