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
package com.keygenqt.ps.route.projects

import com.keygenqt.core.extension.getNumberParam
import com.keygenqt.core.extension.receiveValidate
import com.keygenqt.ps.extension.checkRoleAdmin
import com.keygenqt.ps.route.projects.elements.ProjectRequest
import com.keygenqt.ps.service.ProjectsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.projectsRoute() {
    val service: ProjectsService by inject()

    route("/projects") {
        post {
            call.checkRoleAdmin()

            val request = call.receiveValidate<ProjectRequest>(
                "Error creating project, please check the correctness of data entry"
            )

            call.respond(
                service.insert(
                    category = request.category,
                    publicImage = request.publicImage,
                    title = request.title,
                    url = request.url,
                    urlGitHub = request.urlGitHub,
                    description = request.description,
                    isPublished = request.isPublished,
                    uploads = request.uploads
                )
            )
        }
        put("/{id}") {
            call.checkRoleAdmin()

            val request = call.receiveValidate<ProjectRequest>(
                "Error update project, please check the correctness of data entry"
            )

            call.respond(
                service.update(
                    id = call.getNumberParam(),
                    category = request.category,
                    publicImage = request.publicImage,
                    title = request.title,
                    url = request.url,
                    urlGitHub = request.urlGitHub,
                    description = request.description,
                    isPublished = request.isPublished,
                    uploads = request.uploads
                )
            )
        }
    }
}
