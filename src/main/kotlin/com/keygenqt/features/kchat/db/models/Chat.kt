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
