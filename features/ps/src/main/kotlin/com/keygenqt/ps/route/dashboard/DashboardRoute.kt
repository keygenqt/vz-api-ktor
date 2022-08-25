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
package com.keygenqt.ps.route.dashboard

import com.keygenqt.ps.base.Bash
import com.keygenqt.ps.route.dashboard.elements.CountResponse
import com.keygenqt.ps.route.dashboard.elements.HardDiskSizeResponse
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.service.ProjectsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.dashboardRoute() {

    val projectsService: ProjectsService by inject()
    val articlesService: ArticlesService by inject()

    route("/dashboard") {

        get("/hard-disk-size") {

            val result = Bash.exec("df | grep /\$")
                .firstOrNull()
                ?.toString()
                ?.split(" ")
                ?.filter { it.isNotBlank() }

            val name = result?.get(0) ?: ""
            val blocks = result?.get(1)?.toLongOrNull() ?: 0
            val available = result?.get(2)?.toLongOrNull() ?: 0
            val used = result?.get(3)?.toLongOrNull() ?: 0
            val use = result?.get(4)?.replace("%", "")?.toIntOrNull() ?: 0

            call.respond(
                HardDiskSizeResponse(
                    name = name,
                    blocks = blocks,
                    available = available,
                    used = used,
                    use = use,
                )
            )
        }

        get("/projects-count") {
            call.respond(
                CountResponse(
                    count = projectsService.count()
                )
            )
        }

        get("/articles-count") {
            call.respond(
                CountResponse(
                    count = articlesService.count()
                )
            )
        }

        // 1. count projects
        // 2. count articles
        // 3. count followers
        // 4. count public repos
        // 5. project types (web, ios, android, other)
        // 6. list top projects (repos)

        // https://api.github.com/users/keygenqt
        // https://api.github.com/users/keygenqt/repos?per_page=100&page=1
    }
}