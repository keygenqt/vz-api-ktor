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
import com.keygenqt.ps.db.models.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and

class ArticlesService(
    private val db: DatabaseMysql
) {
    /**
     * Get count models
     */
    suspend fun countPublic(): Long = db.transaction {
        ArticleEntity.find { (Articles.isPublished eq true) }.count()
    }

    /**
     * Get all models
     */
    suspend fun getAll(
        connectKey: String
    ): List<Article> = db.transaction {
        ArticleEntity
            .isHas(ArticleEntity::isLike) { (LikesArticle.key eq connectKey) }
            .all()
            .orderBy(Pair(Articles.createAt, SortOrder.DESC))
            .toArticles()
    }

    /**
     * Get all public models
     */
    suspend fun getAllPublic(
        connectKey: String
    ): List<Article> = db.transaction {
        ArticleEntity
            .isHas(ArticleEntity::isLike) { (LikesArticle.key eq connectKey) }
            .find { (Articles.isPublished eq true) }
            .orderBy(Pair(Articles.createAt, SortOrder.DESC))
            .toArticles()
    }

    /**
     * Get model by id
     */
    suspend fun getById(
        connectKey: String,
        id: Int
    ): Article? = db.transaction {
        ArticleEntity
            .isHas(ArticleEntity::isLike) { (LikesArticle.key eq connectKey) }
            .findById(id)
            ?.load(ArticleEntity::uploads)
            ?.toArticle()
    }

    /**
     * Get public model by id
     */
    suspend fun getByIdPublic(
        connectKey: String,
        id: Int
    ): Article? = db.transaction {
        ArticleEntity
            .isHas(ArticleEntity::isLike) { (LikesArticle.key eq connectKey) }
            .find { ((Articles.id eq id) and (Articles.isPublished eq true)) }
            .with(ArticleEntity::uploads)
            .firstOrNull()
            ?.toArticle()
    }

    /**
     * Add model [Article]
     */
    suspend fun insert(
        title: String?,
        description: String?,
        content: String?,
        titleRu: String?,
        descriptionRu: String?,
        contentRu: String?,
        category: ArticleCategory?,
        listImage: String?,
        viewImage: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Article = db.transaction {
        ArticleEntity.new {
            title?.let { this.title = title }
            description?.let { this.description = description }
            content?.let { this.content = content }
            titleRu?.let { this.titleRu = titleRu }
            descriptionRu?.let { this.descriptionRu = descriptionRu }
            contentRu?.let { this.contentRu = contentRu }
            category?.let { this.category = category }
            listImage?.let { this.listImage = listImage }
            viewImage?.let { this.viewImage = viewImage }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.createAt = System.currentTimeMillis()
            this.updateAt = System.currentTimeMillis()
        }.toArticle()
    }

    /**
     * Update model [Article]
     */
    suspend fun update(
        id: Int,
        title: String?,
        description: String?,
        content: String?,
        titleRu: String?,
        descriptionRu: String?,
        contentRu: String?,
        category: ArticleCategory?,
        listImage: String?,
        viewImage: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Article = db.transaction {
        ArticleEntity.findById(id)?.apply {
            title?.let { this.title = title }
            description?.let { this.description = description }
            content?.let { this.content = content }
            titleRu?.let { this.titleRu = titleRu }
            descriptionRu?.let { this.descriptionRu = descriptionRu }
            contentRu?.let { this.contentRu = contentRu }
            category?.let { this.category = category }
            listImage?.let { this.listImage = listImage }
            viewImage?.let { this.viewImage = viewImage }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.updateAt = System.currentTimeMillis()
        }?.toArticle() ?: throw AppException.Error404("Resource not found")
    }
}
