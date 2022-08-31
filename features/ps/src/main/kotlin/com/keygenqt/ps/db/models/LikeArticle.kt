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

object LikesArticle : IntIdTable() {
    val articleId = reference("articleId", Articles).uniqueIndex()
    val key = varchar("key", 255)
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class LikeArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LikeArticleEntity>(LikesArticle)

    var articleId by LikesArticle.articleId
    var key by LikesArticle.key
    var createAt by LikesArticle.createAt
}

@Serializable
data class LikeArticle(
    val id: Int? = null,
    val articleId: Int,
    val key: String,
    val createAt: Long? = null
)

/**
 * Convert to model
 */
fun LikeArticleEntity.toLike() = Like(
    id = id.value,
    projectId = articleId.value,
    key = key,
    createAt = createAt
)

/**
 * Convert list
 */
fun Iterable<LikeArticleEntity>.toLikes(): List<Like> {
    return map { it.toLike() }
}
