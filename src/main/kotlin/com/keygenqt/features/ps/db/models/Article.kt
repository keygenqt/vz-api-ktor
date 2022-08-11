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
    val content: String
)
