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

import com.keygenqt.core.exceptions.AppException
import com.keygenqt.core.extension.getId
import com.keygenqt.core.extension.receiveValidate
import com.keygenqt.ps.route.articles.elements.ArticleRequest
import com.keygenqt.ps.service.ArticlesService
import io.ktor.server.application.*
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
            call.respond(service.getById(call.getId()) ?: throw AppException.Error404("Model not found"))
        }
        post {
            val request = call.receiveValidate<ArticleRequest>("Error creating article, please check the correctness of data entry")
            call.respond(
                service.insert(
                    category = request.category,
                    title = request.title,
                    description = request.description,
                    content = request.content,
                    isPublished = request.isPublished,
                )
            )
        }
        put("/{id}") {
            val request = call.receiveValidate<ArticleRequest>("Error creating article, please check the correctness of data entry")
            if (service.update(
                    id = call.getId(),
                    category = request.category,
                    title = request.title,
                    description = request.description,
                    content = request.content,
                    isPublished = request.isPublished,
                )
            ) {
                call.respond(request)
            } else {
                throw AppException.Error500()
            }
        }
    }
}