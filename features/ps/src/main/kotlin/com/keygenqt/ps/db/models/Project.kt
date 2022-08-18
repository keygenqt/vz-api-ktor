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
import org.jetbrains.exposed.sql.Table

object Projects : IntIdTable() {
    val icon = varchar("icon", 255)
    val title = varchar("title", 255)
    val description = varchar("description", 255)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ProjectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProjectEntity>(Projects)

    var icon by Projects.icon
    var title by Projects.title
    var description by Projects.description
    var createAt by Projects.createAt
    var updateAt by Projects.updateAt
}

@Serializable
data class Project(
    val id: Int? = null,
    val icon: String,
    val title: String,
    val description: String,
    val createAt: Long,
    val updateAt: Long,
)

/**
 * Convert to model
 */
fun ProjectEntity.toProject() = Project(
    id = id.value,
    icon = icon,
    title = title,
    description = description,
    createAt = createAt,
    updateAt = updateAt,
)

/**
 * Convert list
 */
fun SizedIterable<ProjectEntity>.toProjects(): List<Project> {
    return map { it.toProject() }
}
