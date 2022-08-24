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

class UploadsService(
    val db: DatabaseMysql,
) {
    /**
     * Get all models
     */
    suspend fun getAll(): List<Upload> = db.transaction {
        UploadEntity.all().toUploads()
    }

    /**
     * Get model by id
     */
    suspend fun getById(id: Int): Upload? = db.transaction {
        UploadEntity.findById(id)?.toUpload()
    }

    /**
     * Add model [Upload]
     */
    suspend fun insert(
        fileName: String?,
        fileMime: String?,
        originalFileName: String?,
    ): Upload = db.transaction {
        UploadEntity.new {

            fileName?.let { this.fileName = fileName }
            fileMime?.let { this.fileMime = fileMime }
            originalFileName?.let { this.originalFileName = originalFileName }

            this.createAt = System.currentTimeMillis()
        }.toUpload()
    }

    suspend fun deleteByFileName(
        fileName: String,
    ) = db.transaction {
        UploadEntity.find { (Uploads.fileName eq fileName) }.firstOrNull()?.delete()
    }
}
