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
package com.keygenqt.ps.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SizedIterable

object Tokens : IntIdTable() {
    val userId = reference("userId", Users)
    val deviceId = varchar("deviceId", 255).uniqueIndex()
    val token = varchar("token", 255).uniqueIndex()
    val dateUpdated = long("dateUpdated")
}

/**
 * Exposed entity
 */
class TokenEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TokenEntity>(Tokens)

    var userId by Tokens.userId
    var deviceId by Tokens.deviceId
    var token by Tokens.token
    var dateUpdated by Tokens.dateUpdated
}

@Serializable
data class Token(
    val userId: Int,
    val deviceId: String,
    val token: String,
    val dateUpdated: Long,
)

/**
 * Convert
 */
fun TokenEntity.toToken() = Token(
    userId = userId.value,
    deviceId = deviceId,
    token = token,
    dateUpdated = dateUpdated,
)

/**
 * Convert list
 */
fun SizedIterable<TokenEntity>.toTokens(): List<Token> {
    return map { it.toToken() }
}

