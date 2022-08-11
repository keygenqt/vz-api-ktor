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
package com.keygenqt

import com.keygenqt.base.ConfiguratorApp
import com.keygenqt.features.kchat.base.ConfiguratorKChat
import com.keygenqt.features.ps.base.ConfiguratorPS
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.core.context.startKoin

val configurators: List<ConfiguratorApp> = listOf(
    ConfiguratorPS(),
    ConfiguratorKChat()
)

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {
    // init json
    install(ContentNegotiation) {
        json()
    }

    // init routing
    install(Routing) {
        configurators.forEach { it.initRouting(this) }
    }

    // custom app configure
    configurators.forEach { it.intiConfigure(this) }

    // init koin
    startKoin {
        printLogger()
        modules(configurators.map { it.di() }.toTypedArray().flatten())
    }
}
