package com.keygenqt.features.ps.service

import com.keygenqt.base.DatabaseMysql
import com.keygenqt.features.ps.db.models.Article
import com.keygenqt.features.ps.db.models.Articles
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ArticlesService(
    val db: DatabaseMysql
) {
    suspend fun getAllArticles(limit: Int, offset: Int, search: String?): List<Article> = db.dbQuery {
        Articles.let {
            search?.let { search -> it.select { Articles.title like "%$search%" } } ?: it.selectAll()
        }.orderBy(Articles.id, SortOrder.DESC).limit(limit, offset.toLong()).map { toArticle(it) }
    }

    suspend fun getArticle(id: Int): Article? = db.dbQuery {
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