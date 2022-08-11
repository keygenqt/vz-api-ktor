package com.keygenqt.features.ps.service

import com.keygenqt.base.DatabaseMysql
import com.keygenqt.features.ps.db.models.Project
import com.keygenqt.features.ps.db.models.Projects
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