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

/**
 * Project category
 */
enum class ProjectCategory {
    ANDROID, PC, WEB, IOS, OTHER
}

/**
 * Base language project
 */
enum class ProjectLanguage {
    KOTLIN, JAVASCRIPT, SWIFT, PHP, PYTHON, BASH, OTHER
}

object Projects : IntIdTable() {
    val category = enumeration("category", ProjectCategory::class).default(ProjectCategory.OTHER)
    val language = enumeration("language", ProjectLanguage::class).default(ProjectLanguage.OTHER)
    val publicImage = varchar("publicImage", 255)
    val title = varchar("title", 255)
    val url = varchar("url", 255).default("")
    val description = varchar("description", 255)
    val isPublished = bool("isPublished").default(false)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ProjectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProjectEntity>(Projects)

    var category by Projects.category
    var language by Projects.language
    var publicImage by Projects.publicImage
    var title by Projects.title
    var url by Projects.url
    var description by Projects.description
    var isPublished by Projects.isPublished
    var createAt by Projects.createAt
    var updateAt by Projects.updateAt

    var uploads by UploadEntity via ProjectUploads
}

@Serializable
data class Project(
    val id: Int? = null,
    val category: ProjectCategory,
    val language: ProjectLanguage,
    val publicImage: String,
    val title: String,
    val url: String,
    val description: String,
    val isPublished: Boolean,
    val createAt: Long,
    val updateAt: Long,
    val uploads: List<Upload>,
)

/**
 * Convert to model
 */
fun ProjectEntity.toProject() = Project(
    id = id.value,
    category = category,
    language = language,
    publicImage = publicImage,
    title = title,
    url = url,
    description = description,
    isPublished = isPublished,
    createAt = createAt,
    updateAt = updateAt,
    uploads = uploads.toUploads().reversed(),
)

/**
 * Convert list
 */
fun SizedIterable<ProjectEntity>.toProjects(): List<Project> {
    return map { it.toProject() }
}
