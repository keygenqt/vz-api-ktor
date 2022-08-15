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
package com.keygenqt.kchat.route

import com.keygenqt.kchat.service.ChatService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chatsRoute() {
    val service: ChatService by inject()

    route("/chats") {
        get {
            val search = call.parameters["search"]
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 5
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            call.respond(service.getAllChats(limit, offset, search))
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provide id")
            val chat = service.getChat(id)
            if (chat == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(chat)
        }
    }
}
