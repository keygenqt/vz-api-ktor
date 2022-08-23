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
package com.keygenqt.kchat.base

import com.keygenqt.core.base.ConfiguratorApp
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.kchat.route.chatsRoute
import com.keygenqt.kchat.service.ChatService
import com.keygenqt.kchat.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfiguratorKChat : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql

    override fun di(): Array<Module> {
        return arrayOf(
            module {
                single { ChatService(db) }
            }
        )
    }

    override fun Route.routing() {
        route("/${Constants.BASE_API_PATH}") {
            chatsRoute()
        }
    }

    override fun Application.configure() {
        db = DatabaseMysql(
            config = environment.config.property("${Constants.APP_CONFIG}.config").getString(),
            migration = environment.config.property("${Constants.APP_CONFIG}.migration").getString()
        )
    }

    override fun SessionsConfig.session() {}
    override fun AuthenticationConfig.authentication() {}
}
