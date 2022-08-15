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
import com.keygenqt.ps.db.models.Article
import com.keygenqt.ps.db.models.Articles
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ArticlesService(
    val db: DatabaseMysql
) {
    suspend fun getAllArticles(limit: Int, offset: Int, search: String?): List<Article> = db.transaction {
        Articles.let {
            search?.let { search -> it.select { Articles.title like "%$search%" } } ?: it.selectAll()
        }.orderBy(Articles.id, SortOrder.DESC).limit(limit, offset.toLong()).map { toArticle(it) }
    }

    suspend fun getArticle(id: Int): Article? = db.transaction {
        Articles.select {
            (Articles.id eq id)
        }.mapNotNull { toArticle(it) }.singleOrNull()
    }

    private fun toArticle(row: ResultRow): Article = Article(
        id = row[Articles.id],
        icon = row[Articles.icon],
        title = row[Articles.title],
        description = row[Articles.description],
        content = row[Articles.content],
        dateUpdated = row[Articles.dateUpdated]
    )
}
