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

object Videos : IntIdTable() {
    val title = varchar("title", 255)
    val description = varchar("description", 500)
    val titleRu = varchar("titleRu", 255).nullable()
    val descriptionRu = varchar("descriptionRu", 500).nullable()
    val image = varchar("image", 255)
    val url = varchar("url", 255)
    val isPublished = bool("isPublished").default(false)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class VideoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntSubQueryEntityClass<VideoEntity>(Videos)

    var title by Videos.title
    var description by Videos.description
    var titleRu by Videos.titleRu
    var descriptionRu by Videos.descriptionRu
    var image by Videos.image
    var url by Videos.url
    var isPublished by Videos.isPublished
    var createAt by Videos.createAt
    var updateAt by Videos.updateAt

    var uploads by UploadEntity via VideoUploads
}

@Serializable
data class Video(
    val id: Int? = null,
    val title: String,
    val description: String,
    val titleRu: String?,
    val descriptionRu: String?,
    val image: String,
    val url: String,
    val isPublished: Boolean,
    val createAt: Long,
    val updateAt: Long,
    val uploads: List<Upload>,
)

/**
 * Convert to model
 */
fun VideoEntity.toVideo() = Video(
    id = id.value,
    title = title,
    description = description,
    titleRu = titleRu,
    descriptionRu = descriptionRu,
    image = image,
    url = url,
    isPublished = isPublished,
    createAt = createAt,
    updateAt = updateAt,
    uploads = uploads.toUploads().reversed(),
)

/**
 * Convert list
 */
fun Iterable<VideoEntity>.toVideos(): List<Video> {
    return map { it.toVideo() }
}
