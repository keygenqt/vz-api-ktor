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
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.SizedCollection

class ProjectsService(
    val db: DatabaseMysql,
) {
    /**
     * Get count models
     */
    suspend fun count(): Long = db.transaction {
        ProjectEntity.count()
    }

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
        ProjectEntity.findById(id)?.load(ProjectEntity::uploads)?.toProject()
    }

    /**
     * Add model [Article]
     */
    suspend fun insert(
        category: ProjectCategory?,
        language: ProjectLanguage?,
        publicImage: String?,
        title: String?,
        url: String?,
        description: String?,
        isPublished: Boolean?,
        uploads: List<Int>,
    ): Project = db.transaction {
        ProjectEntity.new {

            category?.let { this.category = category }
            language?.let { this.language = language }
            publicImage?.let { this.publicImage = publicImage }
            title?.let { this.title = title }
            url?.let { this.url = url }
            description?.let { this.description = description }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.createAt = System.currentTimeMillis()
            this.updateAt = System.currentTimeMillis()
        }.toProject()
    }

    /**
     * Update model [Article]
     */
    suspend fun update(
        id: Int,
        category: ProjectCategory?,
        language: ProjectLanguage?,
        publicImage: String?,
        title: String?,
        url: String?,
        description: String?,
        isPublished: Boolean?,
        uploads: List<Int>,
    ): Boolean = db.transaction {
        ProjectEntity.findById(id)?.apply {

            category?.let { this.category = category }
            language?.let { this.language = language }
            publicImage?.let { this.publicImage = publicImage }
            title?.let { this.title = title }
            url?.let { this.url = url }
            description?.let { this.description = description }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.updateAt = System.currentTimeMillis()
        }.let { it != null }
    }
}
