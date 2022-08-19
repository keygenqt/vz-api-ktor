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
package com.keygenqt.ps.route.articles

import com.keygenqt.core.exceptions.AppRuntimeException
import com.keygenqt.ps.route.articles.elements.ArticleRequest
import com.keygenqt.ps.service.ArticlesService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.articlesRoute() {

    val service: ArticlesService by inject()

    route("/articles") {
        get {
            call.respond(service.getAll())
        }
        get("/{id}") {
            val id = call
                .parameters["id"]
                ?.toIntOrNull() ?: throw AppRuntimeException.Error404("Must provide id number")

            call.respond(service.getById(id) ?: throw AppRuntimeException.Error404("Model not found"))
        }
        post {
            val request = call.receive<ArticleRequest>()
            call.respond(
                service.insert(
                    category = request.category,
                    title = request.title,
                    description = request.description,
                    content = request.content,
                )
            )
        }
        put("/{id}") {

            val id = call
                .parameters["id"]
                ?.toIntOrNull() ?: throw AppRuntimeException.Error404("Must provide id number")

            val request = call.receive<ArticleRequest>()

            if (service.update(
                    id = id,
                    category = request.category,
                    title = request.title,
                    description = request.description,
                    content = request.content,
                )
            ) {
                call.respond(request)
            } else {
                throw AppRuntimeException.Error500()
            }
        }
    }
}
