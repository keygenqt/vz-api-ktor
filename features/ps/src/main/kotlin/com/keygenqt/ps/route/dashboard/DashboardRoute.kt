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

import com.keygenqt.core.base.Bash
import com.keygenqt.ps.db.models.RepoCategory
import com.keygenqt.ps.route.dashboard.elements.CountResponse
import com.keygenqt.ps.route.dashboard.elements.HardDiskSizeResponse
import com.keygenqt.ps.route.dashboard.elements.InfoResponse
import com.keygenqt.ps.route.dashboard.elements.InfoTypesResponse
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.service.GitHubRepoService
import com.keygenqt.ps.service.GitHubUserService
import com.keygenqt.ps.service.ProjectsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.dashboardRoute() {
    val gitHubRepoService: GitHubRepoService by inject()
    val gitHubUserService: GitHubUserService by inject()
    val projectsService: ProjectsService by inject()
    val articlesService: ArticlesService by inject()

    route("/dashboard") {
        // 0. size disk
        get("/hard-disk-size") {
            val result = Bash.exec("df | grep /\$")
                .firstOrNull()
                ?.toString()
                ?.split(" ")
                ?.filter { it.isNotBlank() }

            val name = result?.get(0) ?: ""
            val blocks = (result?.get(1)?.toLongOrNull() ?: 0) * 1024
            val available = (result?.get(2)?.toLongOrNull() ?: 0) * 1024
            val used = (result?.get(3)?.toLongOrNull() ?: 0) * 1024
            val use = result?.get(4)?.replace("%", "")?.toIntOrNull() ?: 0

            call.respond(
                HardDiskSizeResponse(
                    name = name,
                    blocks = blocks,
                    available = available,
                    used = used,
                    use = use
                )
            )
        }

        // 1. count projects
        get("/projects-count") {
            call.respond(
                CountResponse(
                    count = projectsService.countPublic()
                )
            )
        }

        // 2. count articles
        get("/articles-count") {
            call.respond(
                CountResponse(
                    count = articlesService.countPublic()
                )
            )
        }

        // 3. count followers
        get("/followers-count") {
            call.respond(
                InfoResponse(
                    count = gitHubUserService.getLast().followersCount,
                    data = gitHubUserService.followersCounts()
                )
            )
        }

        // 3. count public repos
        get("/repos-count") {
            call.respond(
                InfoResponse(
                    count = gitHubUserService.getLast().publicReposCount,
                    data = gitHubUserService.reposCounts()
                )
            )
        }

        // 3. month
        get("/repos-types") {
            call.respond(
                InfoTypesResponse(
                    web = gitHubRepoService.getReposMonthTypes()[RepoCategory.WEB] ?: emptyList(),
                    android = gitHubRepoService.getReposMonthTypes()[RepoCategory.ANDROID] ?: emptyList(),
                    ios = gitHubRepoService.getReposMonthTypes()[RepoCategory.IOS] ?: emptyList(),
                    other = gitHubRepoService.getReposMonthTypes()[RepoCategory.OTHER] ?: emptyList()
                )
            )
        }

        // 3. list popular
        get("/repos-popular") {
            call.respond(gitHubRepoService.getAllRepos().subList(0, 7))
        }
    }
}
