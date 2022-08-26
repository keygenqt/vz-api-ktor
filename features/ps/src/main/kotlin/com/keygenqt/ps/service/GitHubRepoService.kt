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
package com.keygenqt.ps.service

import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.core.exceptions.AppException
import com.keygenqt.core.utils.Utils
import com.keygenqt.ps.db.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.deleteAll


class GitHubRepoService(
    private val db: DatabaseMysql,
    private val client: HttpClient,
) {

    /**
     * Get repos types by month
     */
    suspend fun getReposMonthTypes(): Map<RepoCategory, List<Long>> {
        val result = mutableMapOf<RepoCategory, MutableList<Long>>()
        getAllRepos().forEach { repo ->
            repo.category?.let { category ->
                val month = repo.gitHubCreateAt.month.value - 1
                if (!result.containsKey(category)) {
                    result[category] = LongArray(12) { 0 }.toMutableList()
                }
                result[category]?.set(month, result[category]?.get(month)?.plus(1) ?: 0)
            }

        }
        return result
    }

    /**
     * Get last [GitHubUser]
     */
    suspend fun getAllRepos(): List<GitHubRepo> = db.transaction {

        val models = GitHubRepoEntity
            .all()
            .orderBy(GitHubRepos.stargazersCount to SortOrder.DESC)
            .toGitHubRepos()

        if (models.isNotEmpty()) {
            if ((models.first().createAt ?: 0) + 3600000 /* 1h */ > System.currentTimeMillis()) {
                models
            } else {
                getAllPublicRepos()
            }
        } else {
            getAllPublicRepos()
        }
    }

    /**
     * Add new [GitHubRepo]
     */
    private suspend fun insert(
        model: GitHubRepo,
    ): GitHubRepo = db.transaction {
        GitHubRepoEntity.new {
            this.language = model.language ?: ""
            this.name = model.name
            this.description = model.description ?: ""
            this.url = model.url
            this.topics = model.topics?.joinToString(", ") ?: ""
            this.stargazersCount = model.stargazersCount
            this.gitHubCreateAt = model.gitHubCreateAt.toString()
            this.category = this.parseCategory()
            this.createAt = model.createAt ?: System.currentTimeMillis()
        }.toGitHubRepo()
    }

    /**
     * Clear table [GitHubRepos]
     */
    private suspend fun clear() = db.transaction {
        GitHubRepos.deleteAll()
    }

    /**
     * Get all public repos
     */
    private suspend fun getAllPublicRepos(): List<GitHubRepo> {
        var page = 1
        val result = mutableListOf<GitHubRepo>()
        while (true) {
            val response = Utils.requestAndCatch {
                client.get("https://api.github.com/users/keygenqt/repos?per_page=100&page=$page")
                    .body<List<GitHubRepo>>()
            }

            if (response.isEmpty()) {
                clear()
                return result
                    .map { insert(it) }
                    .sortedBy { it.stargazersCount }
                    .reversed()
            } else {
                if (page > 20) {
                    throw AppException.Error400("Many request pages github")
                }
                result.addAll(response)
            }

            page += 1
        }
    }

    private fun GitHubRepoEntity.parseCategory(): RepoCategory {
        if (name.contains("android")
            || topics.contains("android")
            || topics.contains("compose")
        ) {
            return RepoCategory.ANDROID
        }
        if (name.contains("ios")
            || topics.contains("ios")
            || topics.contains("ios")
            || topics.contains("swiftUI")
            || topics.contains("swift")
        ) {
            return RepoCategory.IOS
        }
        if (name.contains("yii2")
            || name.contains("react")
            || name.contains("api")
            || topics.contains("api")
            || topics.contains("website")
            || topics.contains("react")
            || topics.contains("yii2")
        ) {
            return RepoCategory.WEB
        }
        return RepoCategory.OTHER
    }
}
