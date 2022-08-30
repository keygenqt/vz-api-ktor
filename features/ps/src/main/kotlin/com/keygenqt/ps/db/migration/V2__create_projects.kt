package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.Projects
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V2__create_projects : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Projects)

            Projects.insert {
                it[title] = "Personal site"
                it[publicImage] = ""
                it[description] = "Personal website project. Also contains API, admin panel for all my open source projects"
                it[createAt] = "2022-07-14T19:50:40Z".toInstant().epochSeconds * 1000
                it[updateAt] = "2022-07-14T19:50:40Z".toInstant().epochSeconds * 1000
            }
        }
    }
}
