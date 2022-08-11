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
package com.keygenqt.features.kchat.service

import com.keygenqt.base.DatabaseMysql
import com.keygenqt.features.kchat.db.models.Chat
import com.keygenqt.features.kchat.db.models.Chats
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ChatService(
    val db: DatabaseMysql
) {
    suspend fun getAllChats(limit: Int, offset: Int, search: String?): List<Chat> = db.dbQuery {
        Chats.let {
            search?.let { search -> it.select { Chats.name like "%$search%" } } ?: it.selectAll()
        }.orderBy(Chats.id, SortOrder.DESC).limit(limit, offset.toLong()).map { toChat(it) }
    }

    suspend fun getChat(id: Int): Chat? = db.dbQuery {
        Chats.select {
            (Chats.id eq id)
        }.mapNotNull { toChat(it) }.singleOrNull()
    }

    private fun toChat(row: ResultRow): Chat = Chat(
        id = row[Chats.id],
        userId = row[Chats.userId],
        name = row[Chats.name],
        dateUpdated = row[Chats.dateUpdated]
    )
}
