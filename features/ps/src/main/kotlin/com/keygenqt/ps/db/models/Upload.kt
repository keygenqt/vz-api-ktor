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

import com.keygenqt.core.db.IntSubQueryEntityClass
import com.keygenqt.core.db.IntSubQueryEntityClass.Companion.isHas
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Uploads : IntIdTable() {
    val fileName = varchar("fileName", 255).uniqueIndex()
    val fileMime = varchar("fileMime", 255)
    val originalFileName = varchar("originalFileName", 255)
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class UploadEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntSubQueryEntityClass<UploadEntity>(Uploads)

    var fileName by Uploads.fileName
    var fileMime by Uploads.fileMime
    var originalFileName by Uploads.originalFileName
    var createAt by Uploads.createAt

    val isRelationArticle by Boolean isHas ArticleUploads.upload
    val isRelationProject by Boolean isHas ProjectUploads.upload
}

@Serializable
data class Upload(
    val id: Int? = null,
    val fileName: String,
    val fileMime: String,
    val originalFileName: String,
    val createAt: Long? = null,
    val isRelationArticle: Boolean? = null,
    val isRelationProject: Boolean? = null
)

/**
 * Convert to model
 */
fun UploadEntity.toUpload() = Upload(
    id = id.value,
    fileName = fileName,
    fileMime = fileMime,
    originalFileName = originalFileName,
    createAt = createAt,
    isRelationArticle = isRelationArticle,
    isRelationProject = isRelationProject
)

/**
 * Convert list
 */
fun Iterable<UploadEntity>.toUploads(): List<Upload> {
    return map { it.toUpload() }
}
