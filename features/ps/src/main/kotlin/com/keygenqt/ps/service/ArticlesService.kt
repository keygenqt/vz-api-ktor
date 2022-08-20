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
import com.keygenqt.ps.db.models.*

class ArticlesService(
    val db: DatabaseMysql,
) {
    /**
     * Get all models
     */
    suspend fun getAll(): List<Article> = db.transaction {
        ArticleEntity.all().toArticles()
    }

    /**
     * Get model by id
     */
    suspend fun getById(id: Int): Article? = db.transaction {
        ArticleEntity.findById(id)?.toArticle()
    }

    /**
     * Add model [Article]
     */
    suspend fun insert(
        category: ArticleCategory?,
        title: String?,
        description: String?,
        content: String?,
        isPublished: Boolean?,
    ): Article = db.transaction {
        ArticleEntity.new {

            category?.let { this.category = category }
            title?.let { this.title = title }
            description?.let { this.description = description }
            content?.let { this.content = content }
            isPublished?.let { this.isPublished = isPublished }

            this.createAt = System.currentTimeMillis()
            this.updateAt = System.currentTimeMillis()
        }.toArticle()
    }

    /**
     * Update model [Article]
     */
    suspend fun update(
        id: Int,
        category: ArticleCategory?,
        title: String?,
        description: String?,
        content: String?,
        isPublished: Boolean?,
    ): Boolean = db.transaction {
        ArticleEntity.findById(id)?.apply {

            category?.let { this.category = category }
            title?.let { this.title = title }
            description?.let { this.description = description }
            content?.let { this.content = content }
            isPublished?.let { this.isPublished = isPublished }

            this.updateAt = System.currentTimeMillis()
        }.let { it != null }
    }
}
