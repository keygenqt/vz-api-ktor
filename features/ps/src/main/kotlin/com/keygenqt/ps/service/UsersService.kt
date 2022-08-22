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

import com.keygenqt.core.base.Password
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.ps.db.models.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.mindrot.jbcrypt.BCrypt

class UsersService(
    private val db: DatabaseMysql,
) {

    /**
     * Get all models
     */
    suspend fun getAll(): List<Project> = db.transaction {
        ProjectEntity.all().toProjects()
    }

    /**
     * Get model by id
     */
    suspend fun getById(id: Int): Project? = db.transaction {
        ProjectEntity.findById(id)?.toProject()
    }

    /**
     * Get user by id with load tokens
     */
    suspend fun getByIdTokens(id: Int) = db.transaction {
        UserEntity
            .findById(id)
            ?.load(UserEntity::tokens)
            ?.toUser()
    }

    /**
     * Get user with check password for auth
     */
    suspend fun findUserByAuth(
        email: String?,
        password: String?,
    ) = db.transaction {
        UserEntity
            .find { (Users.email eq (email ?: "")) }
            .with(UserEntity::tokens)
            .firstOrNull()
            ?.let { entity ->
                if (Password.validate(password, entity.password)) {
                    entity.toUser()
                } else {
                    return@transaction null
                }
            }
    }
}
