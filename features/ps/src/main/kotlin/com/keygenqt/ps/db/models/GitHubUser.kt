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
package com.keygenqt.ps.db.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SizedIterable

object GitHubUsers : IntIdTable() {
    val uniqueKey = varchar("uniqueKey", 32).uniqueIndex()
    val publicReposCount = long("publicReposCount")
    val followersCount = long("followersCount")
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class GitHubUserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GitHubUserEntity>(GitHubUsers)

    var publicReposCount by GitHubUsers.publicReposCount
    var followersCount by GitHubUsers.followersCount
    var createAt by GitHubUsers.createAt
}

@Serializable
data class GitHubUser(
    @SerialName("public_repos") val publicReposCount: Long,
    @SerialName("followers") val followersCount: Long,
    val createAt: Long? = null
)

/**
 * Convert to model
 */
fun GitHubUserEntity.toGitHubUser() = GitHubUser(
    publicReposCount = publicReposCount,
    followersCount = followersCount,
    createAt = createAt
)

/**
 * Convert list
 */
fun Iterable<GitHubUserEntity>.toGitHubUsers(): List<GitHubUser> {
    return map { it.toGitHubUser() }
}
