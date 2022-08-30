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

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SizedIterable

/**
 * Category article
 */
enum class ArticleCategory {
    ANDROID, WEB, IOS, OTHER
}

object Articles : IntIdTable() {
    val category = enumeration("category", ArticleCategory::class).default(ArticleCategory.OTHER)
    val publicImage = varchar("publicImage", 255)
    val title = varchar("title", 255)
    val description = varchar("description", 500)
    val content = text("content")
    val isPublished = bool("isPublished").default(false)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ArticleEntity>(Articles)

    var category by Articles.category
    var publicImage by Articles.publicImage
    var title by Articles.title
    var description by Articles.description
    var content by Articles.content
    var isPublished by Articles.isPublished
    var createAt by Articles.createAt
    var updateAt by Articles.updateAt

    var uploads by UploadEntity via ArticleUploads
}

@Serializable
data class Article(
    val id: Int? = null,
    val category: ArticleCategory,
    val publicImage: String,
    val title: String,
    val description: String,
    val content: String,
    val isPublished: Boolean,
    val createAt: Long,
    val updateAt: Long,
    val uploads: List<Upload>
)

/**
 * Convert to model
 */
fun ArticleEntity.toArticle() = Article(
    id = id.value,
    category = category,
    publicImage = publicImage,
    title = title,
    description = description,
    content = content,
    isPublished = isPublished,
    createAt = createAt,
    updateAt = updateAt,
    uploads = uploads.toUploads().reversed()
)

/**
 * Convert list
 */
fun Iterable<ArticleEntity>.toArticles(): List<Article> {
    return map { it.toArticle() }
}
