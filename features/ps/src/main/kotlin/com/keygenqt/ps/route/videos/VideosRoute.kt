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
import com.keygenqt.core.extension.getNumberParam
import com.keygenqt.core.extension.receiveValidate
import com.keygenqt.ps.extension.checkRoleAdmin
import com.keygenqt.ps.extension.connectKey
import com.keygenqt.ps.route.videos.elements.VideoRequest
import com.keygenqt.ps.service.VideosService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.videosRoute() {
    val videosService: VideosService by inject()

    route("/videos") {
        get("/all") {
            call.respond(
                videosService.getAll(
                    connectKey = call.connectKey()
                )
            )
        }

        get("/all/{id}") {
            call.respond(
                videosService.getById(
                    connectKey = call.connectKey(),
                    id = call.getNumberParam()
                )
                    ?: throw AppException.Error404("Project not found")
            )
        }

        post {
            call.checkRoleAdmin()

            val request = call.receiveValidate<VideoRequest>(
                "Error creating video, please check the correctness of data entry"
            )

            call.respond(
                videosService.insert(
                    title = request.title,
                    description = request.description,
                    titleRu = request.titleRu,
                    descriptionRu = request.descriptionRu,
                    image = request.image,
                    url = request.url,
                    isPublished = request.isPublished,
                    uploads = request.uploads
                )
            )
        }
        put("/{id}") {
            call.checkRoleAdmin()

            val request = call.receiveValidate<VideoRequest>(
                "Error update video, please check the correctness of data entry"
            )

            call.respond(
                videosService.update(
                    id = call.getNumberParam(),
                    title = request.title,
                    description = request.description,
                    titleRu = request.titleRu,
                    descriptionRu = request.descriptionRu,
                    image = request.image,
                    url = request.url,
                    isPublished = request.isPublished,
                    uploads = request.uploads
                )
            )
        }
    }
}
