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
import com.keygenqt.core.exceptions.md5Hex
import com.keygenqt.ps.db.models.UserEntity
import com.keygenqt.ps.db.models.Users
import com.keygenqt.ps.route.articles.articlesRoute
import com.keygenqt.ps.route.articles.guestArticlesRoute
import com.keygenqt.ps.route.videos.guestVideosRoute
import com.keygenqt.ps.route.auth.authRoute
import com.keygenqt.ps.route.connects.connectsRoute
import com.keygenqt.ps.route.connects.guestConnectsRoute
import com.keygenqt.ps.route.dashboard.dashboardRoute
import com.keygenqt.ps.route.projects.guestProjectsRoute
import com.keygenqt.ps.route.projects.projectsRoute
import com.keygenqt.ps.route.upload.guestUploadRoute
import com.keygenqt.ps.route.upload.uploadRoute
import com.keygenqt.ps.route.videos.videosRoute
import com.keygenqt.ps.service.*
import com.keygenqt.ps.utils.Constants
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

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
                single { LikesArticleService(db) }
                single { LikesProjectService(db) }
                single { ConnectsService(db) }
                single { VideosService(db) }

                // GitHub services
                HttpClient(CIO) {
                    install(HttpCache)
                    install(ContentNegotiation) {
                        json(
                            Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                    expectSuccess = true
                }.let { client ->
                    single {
                        GitHubUserService(
                            db = db,
                            client = client
                        )
                    }
                    single {
                        GitHubRepoService(
                            db = db,
                            client = client
                        )
                    }
                }
            }
        )
    }

    override fun Route.routing() {
        "/${Constants.BASE_API_PATH}".let { basePath ->
            route(basePath) {
                authRoute()
                guestUploadRoute()
                guestArticlesRoute()
                guestProjectsRoute()
                guestConnectsRoute()
                guestVideosRoute()
            }
            authenticate(jwtAuth, sessionAuth) {
                route(basePath) {
                    uploadRoute()
                    dashboardRoute()
                    articlesRoute()
                    connectsRoute()
                    projectsRoute()
                    videosRoute()
                }
            }
        }
    }

    override fun Application.configure() {
        with(environment.config) {
            // auth secret
            appConf = LoaderConfig.loadProperties(this.property("${Constants.APP_CONFIG}.properties").getString())

            // init db app
            db = DatabaseMysql(
                config = property("${Constants.APP_CONFIG}.config").getString(),
                migration = property("${Constants.APP_CONFIG}.migration").getString()
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
        cookie<UserSession>("ps_session") {
            transform(
                SessionTransportTransformerEncrypt(
                    appConf.getPropOrNull<String>(Constants.Properties.secret).md5Hex(),
                    appConf.getPropOrNull<String>(Constants.Properties.signKey).md5Hex()
                )
            )
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
