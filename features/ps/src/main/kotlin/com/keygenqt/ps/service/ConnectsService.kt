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

import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.core.exceptions.AppException
import com.keygenqt.ps.db.models.*
import org.jetbrains.exposed.sql.SortOrder

class ConnectsService(
    private val db: DatabaseMysql
) {
    /**
     * Get all models
     */
    suspend fun getAll(): List<Connect> = db.transaction {
        ConnectEntity.all().orderBy(Pair(Connects.isSent, SortOrder.ASC)).toConnects()
    }

    /**
     * Get by [Connects.email]
     */
    suspend fun getByEmail(
        email: String?
    ): Connect? = db.transaction {
        ConnectEntity
            .find { Connects.email eq (email ?: "") }
            .firstOrNull()
            ?.toConnect()
    }

    /**
     * Add model [Connect]
     */
    suspend fun insert(
        email: String?,
        isSent: Boolean?,
        locale: String?
    ): Connect = db.transaction {
        ConnectEntity.new {
            email?.let { this.email = email }
            isSent?.let { this.isSent = isSent }
            locale?.let { this.locale = locale }

            this.createAt = System.currentTimeMillis()
            this.updateAt = System.currentTimeMillis()
        }.toConnect()
    }

    /**
     * Update model [Connect]
     */
    suspend fun update(
        id: Int,
        email: String?,
        isSent: Boolean?,
        locale: String?
    ): Connect = db.transaction {
        ConnectEntity.findById(id)?.apply {
            email?.let { this.email = email }
            isSent?.let { this.isSent = isSent }
            locale?.let { this.locale = locale }

            this.updateAt = System.currentTimeMillis()
        }?.toConnect() ?: throw AppException.Error404("Resource not found")
    }
}
