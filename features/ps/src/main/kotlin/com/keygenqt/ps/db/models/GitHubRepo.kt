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

import com.keygenqt.ps.serializers.DateSerializer
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Project category
 */
enum class RepoCategory {
    ANDROID, WEB, IOS, OTHER
}

object GitHubRepos : IntIdTable() {
    val language = varchar("language", 255)
    val stargazersCount = long("stargazersCount")
    val name = varchar("name", 255)
    val description = text("description")
    val url = varchar("url", 255).uniqueIndex()
    val topics = text("topics")
    val gitHubCreateAt = varchar("gitHubCreateAt", 30)
    val category = enumeration("category", RepoCategory::class).default(RepoCategory.OTHER)
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class GitHubRepoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GitHubRepoEntity>(GitHubRepos)

    var category by GitHubRepos.category
    var name by GitHubRepos.name
    var description by GitHubRepos.description
    var url by GitHubRepos.url
    var topics by GitHubRepos.topics
    var language by GitHubRepos.language
    var stargazersCount by GitHubRepos.stargazersCount
    var gitHubCreateAt by GitHubRepos.gitHubCreateAt

    var createAt by GitHubRepos.createAt
}

@Serializable
data class GitHubRepo(
    @SerialName("language")
    val language: String? = "",

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String? = "",

    @SerialName("html_url")
    val url: String,

    @SerialName("topics")
    val topics: List<String>? = listOf(),

    @SerialName("stargazers_count")
    val stargazersCount: Long,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_at")
    val gitHubCreateAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),

    val category: RepoCategory? = null,
    val createAt: Long? = null
)

/**
 * Convert to model
 */
fun GitHubRepoEntity.toGitHubRepo() = GitHubRepo(
    language = language,
    name = name,
    description = description,
    url = url,
    topics = topics.split(", "),
    stargazersCount = stargazersCount,
    gitHubCreateAt = gitHubCreateAt.toLocalDateTime(),
    category = category,
    createAt = createAt
)

/**
 * Convert list
 */
fun Iterable<GitHubRepoEntity>.toGitHubRepos(): List<GitHubRepo> {
    return map { it.toGitHubRepo() }
}
