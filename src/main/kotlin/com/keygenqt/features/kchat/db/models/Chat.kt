package com.keygenqt.features.kchat.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Chats : Table() {
    val id = integer("id").autoIncrement()
    val userId = varchar("userId", 255)
    val name = varchar("name", 255).uniqueIndex()
    val dateUpdated = long("dateUpdated")
    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Chat(
    val id: Int,
    val userId: String,
    val name: String,
    val dateUpdated: Long
)

@Serializable
data class NewChat(
    val id: Int? = null,
    val userId: String,
    val name: String
)