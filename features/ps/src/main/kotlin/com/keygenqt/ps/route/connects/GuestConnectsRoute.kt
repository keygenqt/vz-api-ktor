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
package com.keygenqt.ps.route.connects

import com.keygenqt.core.extension.receiveValidate
import com.keygenqt.ps.route.connects.elements.ConnectRequest
import com.keygenqt.ps.service.ConnectsService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.guestConnectsRoute() {
    val connectsService: ConnectsService by inject()

    route("/connects") {
        post {
            val request = call.receiveValidate<ConnectRequest>(
                "Error creating connect, please check the correctness of data entry"
            )

            connectsService.getByEmail(request.email)?.let {
                connectsService.update(
                    id = it.id!!,
                    email = it.email,
                    locale = request.locale,
                    isSent = false
                )
            } ?: run {
                connectsService.insert(
                    email = request.email,
                    locale = request.locale,
                    isSent = false
                )
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}
