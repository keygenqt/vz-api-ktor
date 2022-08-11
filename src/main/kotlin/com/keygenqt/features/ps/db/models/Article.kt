package com.keygenqt.features.ps.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Articles : Table() {
    val id = integer("id").autoIncrement()
    val icon = varchar("icon", 255)
    val title = varchar("title", 255)
    val description = varchar("description", 255)
    val content = text("content")
    val dateUpdated = long("dateUpdated")
    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Article(
    val id: Int,
    val icon: String,
    val title: String,
    val description: String,
    val content: String,
    val dateUpdated: Long
)

@Serializable
data class NewArticle(
    val id: Int? = null,
    val icon: String,
    val title: String,
    val description: String,
    val content: String,
)