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
import com.keygenqt.ps.db.models.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

class LikesProjectService(
    private val db: DatabaseMysql
) {

    /**
     * Get model by key
     */
    suspend fun getByKeys(
        modelId: Int,
        key: String
    ): Like? = db.transaction {
        LikeProjectEntity
            .find { (LikesProject.key eq key) and (LikesProject.projectId eq modelId) }
            .firstOrNull()
            ?.toLike()
    }

    /**
     * Get model by key
     */
    suspend fun insert(
        modelId: Int,
        key: String
    ): Like = db.transaction {
        LikeProjectEntity.new {
            this.projectId = EntityID(modelId, Projects)
            this.key = key
            this.createAt = System.currentTimeMillis()
        }.toLike()
    }

    /**
     * Get model by key
     */
    suspend fun delete(
        modelId: Int,
        key: String
    ) = db.transaction {
        LikeProjectEntity
            .find { (LikesProject.key eq key) and (LikesProject.projectId eq modelId) }
            .firstOrNull()
            ?.delete()
    }
}
