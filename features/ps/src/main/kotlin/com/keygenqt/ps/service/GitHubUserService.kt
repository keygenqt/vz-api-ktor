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
import com.keygenqt.core.utils.Utils
import com.keygenqt.ps.db.models.GitHubUser
import com.keygenqt.ps.db.models.GitHubUserEntity
import com.keygenqt.ps.db.models.GitHubUsers
import com.keygenqt.ps.db.models.toGitHubUser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.jetbrains.exposed.sql.SortOrder


class GitHubUserService(
    private val db: DatabaseMysql,
    private val client: HttpClient,
) {

    /**
     * Get data statistics count repos
     */
    suspend fun reposCounts() = db.transaction {
        GitHubUserEntity.all().map { it.publicReposCount }
    }

    /**
     * Get data statistics count followers
     */
    suspend fun followersCounts() = db.transaction {
        GitHubUserEntity.all().map { it.followersCount }
    }

    /**
     * Get last [GitHubUser]
     */
    suspend fun getLast(): GitHubUser = db.transaction {

        val model = GitHubUserEntity
            .all()
            .orderBy(GitHubUsers.createAt to SortOrder.DESC)
            .limit(1)
            .firstOrNull()
            ?.toGitHubUser()

        model?.let {
            if ((model.createAt ?: 0) + 3600000 /* 1h */ > System.currentTimeMillis()) {
                model
            } else {
                getUserFromGitHub()
            }
        } ?: run {
            getUserFromGitHub()
        }
    }

    /**
     * Add new [GitHubUser]
     */
    private suspend fun insert(
        model: GitHubUser,
    ): GitHubUser = db.transaction {
        GitHubUserEntity.new {
            this.publicReposCount = model.publicReposCount
            this.followersCount = model.followersCount
            this.createAt = model.createAt ?: System.currentTimeMillis()
        }.toGitHubUser()
    }

    /**
     * Get followers count
     */
    private suspend fun getUserFromGitHub() = Utils.requestAndCatch {
        insert(
            client
                .get("https://api.github.com/users/keygenqt")
                .body<GitHubUser>()
        )
    }
}
