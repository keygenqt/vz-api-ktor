package com.keygenqt.features.ps.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Projects : Table() {
    val id = integer("id").autoIncrement()
    val icon = varchar("icon", 255)
    val title = varchar("title", 255)
    val description = varchar("description", 255)
    val dateUpdated = long("dateUpdated")
    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Project(
    val id: Int,
    val icon: String,
    val title: String,
    val description: String,
    val dateUpdated: Long
)

@Serializable
data class NewProject(
    val id: Int? = null,
    val icon: String,
    val title: String,
    val description: String
)