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


object LikesProject : IntIdTable() {
    val projectId = reference("projectId", Projects)
    val key = varchar("key", 255).uniqueIndex()
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class LikeProjectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LikeProjectEntity>(LikesProject)

    var projectId by LikesProject.projectId
    var key by LikesProject.key
    var createAt by LikesProject.createAt
}

@Serializable
data class Like(
    val id: Int? = null,
    val projectId: Int,
    val key: String,
    val createAt: Long? = null,
)

/**
 * Convert to model
 */
fun LikeProjectEntity.toLike() = Like(
    id = id.value,
    projectId = projectId.value,
    key = key,
    createAt = createAt
)

/**
 * Convert list
 */
fun Iterable<LikeProjectEntity>.toLikes(): List<Like> {
    return map { it.toLike() }
}
