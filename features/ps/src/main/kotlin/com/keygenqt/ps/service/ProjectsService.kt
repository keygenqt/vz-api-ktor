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
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and

class ProjectsService(
    private val db: DatabaseMysql
) {
    /**
     * Get count models
     */
    suspend fun countPublic(): Long = db.transaction {
        ProjectEntity.find { (Projects.isPublished eq true) }.count()
    }

    /**
     * Get all models
     */
    suspend fun getAll(
        connectKey: String
    ): List<Project> = db.transaction {
        ProjectEntity
            .isHas(ProjectEntity::isLike) { (LikesProject.key eq connectKey) }
            .all()
            .orderBy(Pair(Projects.createAt, SortOrder.DESC))
            .toProjects()
    }

    /**
     * Get all public models
     */
    suspend fun getAllPublic(
        connectKey: String
    ): List<Project> = db.transaction {
        ProjectEntity
            .isHas(ProjectEntity::isLike) { (LikesProject.key eq connectKey) }
            .find { (Projects.isPublished eq true) }
            .orderBy(Pair(Projects.createAt, SortOrder.DESC))
            .toProjects()
    }

    /**
     * Get model by id
     */
    suspend fun getById(
        connectKey: String,
        id: Int
    ): Project? = db.transaction {
        ProjectEntity
            .isHas(ProjectEntity::isLike) { (LikesProject.key eq connectKey) }
            .findById(id)
            ?.load(ProjectEntity::uploads)
            ?.toProject()
    }

    /**
     * Get public model by id
     */
    suspend fun getByIdPublic(
        connectKey: String,
        id: Int
    ): Project? = db.transaction {
        ProjectEntity
            .isHas(ProjectEntity::isLike) { (LikesProject.key eq connectKey) }
            .find { ((Projects.id eq id) and (Projects.isPublished eq true)) }
            .with(ProjectEntity::uploads)
            .firstOrNull()
            ?.toProject()
    }

    /**
     * Add model [Article]
     */
    suspend fun insert(
        category: ProjectCategory?,
        publicImage: String?,
        title: String?,
        url: String?,
        urlGitHub: String?,
        urlSnapcraft: String?,
        urlDownload: String?,
        urlYouTube: String?,
        description: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Project = db.transaction {
        ProjectEntity.new {
            category?.let { this.category = category }
            publicImage?.let { this.publicImage = publicImage }
            title?.let { this.title = title }
            url?.let { this.url = url }
            urlGitHub?.let { this.urlGitHub = urlGitHub }
            urlSnapcraft?.let { this.urlSnapcraft = urlSnapcraft }
            urlDownload?.let { this.urlDownload = urlDownload }
            urlYouTube?.let { this.urlYouTube = urlYouTube }
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
        publicImage: String?,
        title: String?,
        url: String?,
        urlGitHub: String?,
        urlSnapcraft: String?,
        urlDownload: String?,
        urlYouTube: String?,
        description: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Project = db.transaction {
        ProjectEntity.findById(id)?.apply {
            category?.let { this.category = category }
            publicImage?.let { this.publicImage = publicImage }
            title?.let { this.title = title }
            url?.let { this.url = url }
            urlGitHub?.let { this.urlGitHub = urlGitHub }
            urlSnapcraft?.let { this.urlSnapcraft = urlSnapcraft }
            urlDownload?.let { this.urlDownload = urlDownload }
            urlYouTube?.let { this.urlYouTube = urlYouTube }
            description?.let { this.description = description }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.updateAt = System.currentTimeMillis()
        }?.toProject() ?: throw AppException.Error404("Resource not found")
    }
}
