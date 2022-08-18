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
package com.keygenqt.core.base

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.core.module.Module

abstract class ConfiguratorApp {

    abstract fun di(): Array<Module>
    abstract fun Route.routing()
    abstract fun Application.configure()
    abstract fun AuthenticationConfig.authentication()
    abstract fun SessionsConfig.session()

    fun intiConfigure(app: Application) {
        app.apply { configure() }
    }

    fun initRouting(route: Route) {
        route.apply { routing() }
    }

    fun initSessions(conf: SessionsConfig) {
        conf.apply { session() }
    }

    fun initAuthentication(conf: AuthenticationConfig) {
        conf.apply { authentication() }
    }
}
