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
import com.keygenqt.core.exceptions.md5
import com.keygenqt.core.extension.getNumberParam
import com.keygenqt.ps.extension.connectKey
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.service.LikesArticleService
import com.keygenqt.ps.service.LikesProjectService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.guestArticlesRoute() {

    val articlesService: ArticlesService by inject()
    val likesArticleService: LikesArticleService by inject()

    route("/articles") {

        get {
            call.respond(
                articlesService.getAllPublic(
                    connectKey = call.connectKey(),
                )
            )
        }

        get("/{id}") {
            call.respond(
                articlesService.getByIdPublic(
                    connectKey = call.connectKey(),
                    id = call.getNumberParam(),
                )
                    ?: throw AppException.Error404("Article not found")
            )
        }

        post("/like/{id}") {
            val like = likesArticleService.getByKeys(
                modelId = call.getNumberParam(),
                key = call.request.host().md5()
            ) ?: run {
                likesArticleService.insert(
                    modelId = call.getNumberParam(),
                    key = call.request.host().md5()
                )
            }
            call.respond(like)
        }

        delete("/like/{id}") {
            likesArticleService.delete(
                modelId = call.getNumberParam(),
                key = call.request.host().md5()
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}
