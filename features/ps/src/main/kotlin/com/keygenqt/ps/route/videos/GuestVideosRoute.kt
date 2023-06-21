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
package com.keygenqt.ps.route.videos

import com.keygenqt.core.exceptions.AppException
import com.keygenqt.core.exceptions.md5
import com.keygenqt.core.extension.getNumberParam
import com.keygenqt.ps.extension.connectKey
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.service.LikesArticleService
import com.keygenqt.ps.service.VideosService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.guestVideosRoute() {
    val videosService: VideosService by inject()

    route("/videos") {
        get {
            call.respond(
                videosService.getAllPublic(
                    connectKey = call.connectKey()
                )
            )
        }

        get("/{id}") {
            call.respond(
                videosService.getByIdPublic(
                    connectKey = call.connectKey(),
                    id = call.getNumberParam()
                )
                    ?: throw AppException.Error404("Article not found")
            )
        }
    }
}
