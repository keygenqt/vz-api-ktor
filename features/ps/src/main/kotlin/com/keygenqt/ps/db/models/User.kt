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

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SizedIterable

/**
 * Role uer in app
 */
enum class UserRole {
    GUEST, ADMIN
}

/**
 * Table users
 */
object Users : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val role = enumeration("role", UserRole::class).default(UserRole.GUEST)
}

/**
 * Exposed entity
 */
class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var email by Users.email
    var password by Users.password
    var role by Users.role

    val tokens by TokenEntity referrersOn Tokens.userId
}

@Serializable
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val role: UserRole,
    val tokens: List<Token>,
): Principal

/**
 * Convert
 */
fun UserEntity.toUser() = User(
    id = id.value,
    email = email,
    password = password,
    role = role,
    tokens = tokens.toTokens(),
)

/**
 * Convert list
 */
fun SizedIterable<UserEntity>.toUsers(): List<User> {
    return map { it.toUser() }
}