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

import com.keygenqt.core.db.IntSubQueryEntityClass
import com.keygenqt.core.db.IntSubQueryEntityClass.Companion.isHas
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Category article
 */
enum class ArticleCategory {
    ANDROID, WEB, IOS, OTHER
}

object Articles : IntIdTable() {

    val title = varchar("title", 255)
    val description = varchar("description", 500)
    val content = text("content")

    val titleRu = varchar("titleRu", 255).nullable()
    val descriptionRu = varchar("descriptionRu", 500).nullable()
    val contentRu = text("contentRu").nullable()

    val category = enumeration("category", ArticleCategory::class).default(ArticleCategory.OTHER)
    val listImage = varchar("listImage", 255)
    val viewImage = varchar("viewImage", 255)
    val isPublished = bool("isPublished").default(false)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntSubQueryEntityClass<ArticleEntity>(Articles)

    var title by Articles.title
    var description by Articles.description
    var content by Articles.content

    var titleRu by Articles.titleRu
    var descriptionRu by Articles.descriptionRu
    var contentRu by Articles.contentRu

    var category by Articles.category
    var listImage by Articles.listImage
    var viewImage by Articles.viewImage
    var isPublished by Articles.isPublished
    var createAt by Articles.createAt
    var updateAt by Articles.updateAt

    var uploads by UploadEntity via ArticleUploads
    val isLike by Boolean isHas LikesArticle.articleId
}

@Serializable
data class Article(
    val id: Int? = null,
    val title: String,
    val description: String,
    val content: String,
    val titleRu: String?,
    val descriptionRu: String?,
    val contentRu: String?,
    val category: ArticleCategory,
    val listImage: String,
    val viewImage: String,
    val isPublished: Boolean,
    val createAt: Long,
    val updateAt: Long,
    val uploads: List<Upload>,
    val isLike: Boolean
)

/**
 * Convert to model
 */
fun ArticleEntity.toArticle() = Article(
    id = id.value,
    title = title,
    description = description,
    content = content,
    titleRu = titleRu,
    descriptionRu = descriptionRu,
    contentRu = contentRu,
    category = category,
    listImage = listImage,
    viewImage = viewImage,
    isPublished = isPublished,
    createAt = createAt,
    updateAt = updateAt,
    uploads = uploads.toUploads().reversed(),
    isLike = isLike
)

/**
 * Convert list
 */
fun Iterable<ArticleEntity>.toArticles(): List<Article> {
    return map { it.toArticle() }
}
