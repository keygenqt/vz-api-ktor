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
package com.keygenqt.ps.components.github

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


class GitHubService(
    private val client: HttpClient,
) {

    /**
     * Get followers count
     */
    suspend fun getFollowersCount() = client
        .get("https://api.github.com/users/keygenqt")
        .body<GitHubUser>()
        .followers

    /**
     * Get public repos count
     */
    suspend fun getPublicReposCount() = client
        .get("https://api.github.com/users/keygenqt")
        .body<GitHubUser>()
        .public_repos

    /**
     * Get public repos
     */
    suspend fun getPublicRepos() = client
        .get("https://api.github.com/users/keygenqt/repos?per_page=100&page=1")
        .body<List<GitHubRepo>>()
}
