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
import com.keygenqt.ps.db.models.Articles.default
import com.keygenqt.ps.db.models.Articles.nullable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Project category
 */
enum class ProjectCategory {
    ANDROID, WEB, IOS, OTHER
}

object Projects : IntIdTable() {
    val title = varchar("title", 255)
    val description = varchar("description", 255)

    val titleRu = varchar("titleRu", 255).nullable()
    val descriptionRu = varchar("descriptionRu", 255).nullable()

    val category = enumeration("category", ProjectCategory::class).default(ProjectCategory.OTHER)
    val publicImage = varchar("publicImage", 255)
    val url = varchar("url", 255).default("")
    val urlGitHub = varchar("urlGitHub", 255).default("")
    val urlSnapcraft = varchar("urlSnapcraft", 255).default("")
    val urlDownload = varchar("urlDownload", 255).default("")
    val urlYouTube = varchar("urlYouTube", 255).default("")
    val isPublished = bool("isPublished").default(false)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ProjectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntSubQueryEntityClass<ProjectEntity>(Projects)

    var title by Projects.title
    var description by Projects.description
    var titleRu by Projects.titleRu
    var descriptionRu by Projects.descriptionRu

    var category by Projects.category
    var publicImage by Projects.publicImage
    var url by Projects.url
    var urlGitHub by Projects.urlGitHub
    var urlSnapcraft by Projects.urlSnapcraft
    var urlDownload by Projects.urlDownload
    var urlYouTube by Projects.urlYouTube
    var isPublished by Projects.isPublished
    var createAt by Projects.createAt
    var updateAt by Projects.updateAt

    var uploads by UploadEntity via ProjectUploads
    val isLike by Boolean isHas LikesProject.projectId
}

@Serializable
data class Project(
    val id: Int? = null,
    val title: String,
    val description: String,
    val titleRu: String?,
    val descriptionRu: String?,
    val category: ProjectCategory,
    val publicImage: String,
    val url: String,
    val urlGitHub: String,
    val urlSnapcraft: String,
    val urlDownload: String,
    val urlYouTube: String,
    val isPublished: Boolean,
    val createAt: Long,
    val updateAt: Long,
    val uploads: List<Upload>,
    val isLike: Boolean
)

/**
 * Convert [ProjectEntity] to model
 */
fun ProjectEntity.toProject() = Project(
    id = id.value,
    title = title,
    description = description,
    titleRu = titleRu,
    descriptionRu = descriptionRu,
    category = category,
    publicImage = publicImage,
    url = url,
    urlGitHub = urlGitHub,
    urlSnapcraft = urlSnapcraft,
    urlDownload = urlDownload,
    urlYouTube = urlYouTube,
    isPublished = isPublished,
    createAt = createAt,
    updateAt = updateAt,
    uploads = uploads.toUploads().reversed(),
    isLike = isLike
)

/**
 * Convert list
 */
fun Iterable<ProjectEntity>.toProjects(): List<Project> {
    return map { it.toProject() }
}
