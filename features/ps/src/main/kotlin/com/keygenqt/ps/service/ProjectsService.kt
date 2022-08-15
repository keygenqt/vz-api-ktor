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
import com.keygenqt.ps.db.models.Project
import com.keygenqt.ps.db.models.Projects
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ProjectsService(
    val db: DatabaseMysql
) {
    suspend fun getAllProjects(limit: Int, offset: Int, search: String?): List<Project> = db.dbQuery {
        Projects.let {
            search?.let { search -> it.select { Projects.title like "%$search%" } } ?: it.selectAll()
        }.orderBy(Projects.id, SortOrder.DESC).limit(limit, offset.toLong()).map { toProject(it) }
    }

    suspend fun getProject(id: Int): Project? = db.dbQuery {
        Projects.select {
            (Projects.id eq id)
        }.mapNotNull { toProject(it) }.singleOrNull()
    }

    private fun toProject(row: ResultRow): Project = Project(
        id = row[Projects.id],
        icon = row[Projects.icon],
        title = row[Projects.title],
        description = row[Projects.description],
        dateUpdated = row[Projects.dateUpdated]
    )
}
