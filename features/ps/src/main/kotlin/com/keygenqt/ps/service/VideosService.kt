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

class VideosService(
    private val db: DatabaseMysql
) {
    /**
     * Get count models
     */
    suspend fun countPublic(): Long = db.transaction {
        VideoEntity.find { (Videos.isPublished eq true) }.count()
    }

    /**
     * Get all models
     */
    suspend fun getAll(
        connectKey: String
    ): List<Video> = db.transaction {
        VideoEntity
            .all()
            .orderBy(Pair(Videos.createAt, SortOrder.DESC))
            .toVideos()
    }

    /**
     * Get all public models
     */
    suspend fun getAllPublic(
        connectKey: String
    ): List<Video> = db.transaction {
        VideoEntity
            .find { (Videos.isPublished eq true) }
            .orderBy(Pair(Videos.createAt, SortOrder.DESC))
            .toVideos()
    }

    /**
     * Get model by id
     */
    suspend fun getById(
        connectKey: String,
        id: Int
    ): Video? = db.transaction {
        VideoEntity
            .findById(id)
            ?.load(VideoEntity::uploads)
            ?.toVideo()
    }

    /**
     * Get public model by id
     */
    suspend fun getByIdPublic(
        connectKey: String,
        id: Int
    ): Video? = db.transaction {
        VideoEntity
            .find { ((Videos.id eq id) and (Videos.isPublished eq true)) }
            .with(VideoEntity::uploads)
            .firstOrNull()
            ?.toVideo()
    }

    /**
     * Add model [Video]
     */
    suspend fun insert(
        title: String?,
        description: String?,
        titleRu: String?,
        descriptionRu: String?,
        image: String?,
        url: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Video = db.transaction {
        VideoEntity.new {
            title?.let { this.title = title }
            description?.let { this.description = description }
            titleRu?.let { this.titleRu = titleRu }
            descriptionRu?.let { this.descriptionRu = descriptionRu }
            image?.let { this.image = image }
            url?.let { this.url = url }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.createAt = System.currentTimeMillis()
            this.updateAt = System.currentTimeMillis()
        }.toVideo()
    }

    /**
     * Update model [Video]
     */
    suspend fun update(
        id: Int,
        title: String?,
        description: String?,
        titleRu: String?,
        descriptionRu: String?,
        image: String?,
        url: String?,
        isPublished: Boolean?,
        uploads: List<Int>
    ): Video = db.transaction {
        VideoEntity.findById(id)?.apply {
            title?.let { this.title = title }
            description?.let { this.description = description }
            titleRu?.let { this.titleRu = titleRu }
            descriptionRu?.let { this.descriptionRu = descriptionRu }
            image?.let { this.image = image }
            url?.let { this.url = url }
            isPublished?.let { this.isPublished = isPublished }

            this.uploads = SizedCollection(uploads.mapNotNull { UploadEntity.findById(it) })
            this.updateAt = System.currentTimeMillis()
        }?.toVideo() ?: throw AppException.Error404("Resource not found")
    }
}
