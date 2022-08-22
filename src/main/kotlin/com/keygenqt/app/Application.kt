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
package com.keygenqt.app

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import com.keygenqt.core.base.ConfiguratorApp
import com.keygenqt.core.base.Password
import com.keygenqt.core.exceptions.AppException
import com.keygenqt.kchat.base.ConfiguratorKChat
import com.keygenqt.ps.base.ConfiguratorPS
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.slf4j.LoggerFactory

val configurators: List<ConfiguratorApp> = listOf(
    ConfiguratorPS(), ConfiguratorKChat()
)

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {

    // logger
    (LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger).apply {
        level = Level.OFF
    }

    // custom app configure
    configurators.forEach { it.intiConfigure(this) }

    // session
    install(Sessions) {
        configurators.forEach { it.initSessions(this) }
    }

    // init json
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        })
    }

    // init auth
    install(Authentication) {
        configurators.forEach { it.initAuthentication(this) }
    }

    // init routing
    install(Routing) {
        // apps
        route("/api") {
            configurators.forEach { it.initRouting(this) }
        }
        // docs
        docsRoute()
    }

    // catch errors
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                status = HttpStatusCode.NotFound,
                message = AppException.Error404().exception
            )
        }
        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = AppException.ErrorAuthorized().exception
            )
        }
        exception<Throwable> { call, cause ->
            if (cause::class.simpleName == "JsonDecodingException" || cause::class.simpleName == "MissingFieldException") {
                AppException.JsonDecodingException(cause.message).let {
                    call.respond(
                        status = it.status,
                        message = it.exception
                    )
                }
            } else if (cause is AppException) {
                cause.let {
                    call.respond(
                        status = it.status,
                        message = it.exception
                    )
                }
            } else {
                println(cause)
                AppException.Error500().let {
                    call.respond(
                        status = it.status,
                        message = it.exception
                    )
                }
            }
        }
    }

    // init koin
    startKoin {
        printLogger()
        modules(configurators.map { it.di() }.toTypedArray().flatten())
    }
}
