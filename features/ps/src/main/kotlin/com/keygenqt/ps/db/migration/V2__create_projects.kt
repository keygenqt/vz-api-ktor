package com.keygenqt.ps.db.migration

import com.keygenqt.core.utils.AppLogger
import com.keygenqt.ps.db.models.Projects
import com.keygenqt.ps.db.inserts.InsertProjects
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.reflect.full.memberFunctions


@Suppress(
    "unused",
    "ClassName"
)
class V2__create_projects : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            // Create table
            SchemaUtils.create(Projects)

            // Get host for url links
            val host = if (AppLogger.isDebug) "http://localhost:8085" else "https://api.keygenqt.com"

            // Insert projects
            InsertProjects::class.memberFunctions.forEach {
                if (it.name.contains("insert")) {
                    it.call(InsertProjects::class.objectInstance, *listOf(it.name, host).toTypedArray())
                }
            }
        }
    }
}