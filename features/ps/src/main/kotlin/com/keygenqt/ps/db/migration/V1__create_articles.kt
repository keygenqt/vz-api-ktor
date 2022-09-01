package com.keygenqt.ps.db.migration

import com.keygenqt.core.utils.AppLogger
import com.keygenqt.ps.db.inserts.InsertArticles
import com.keygenqt.ps.db.models.Articles
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.reflect.full.memberFunctions

@Suppress(
    "unused",
    "ClassName"
)
class V1__create_articles : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            // Create table
            SchemaUtils.create(Articles)

            // Get host for url links
            val host = if (AppLogger.isDebug) "http://localhost:8085" else "https://api.keygenqt.com"

            // Insert projects
            InsertArticles::class.memberFunctions.forEach {
                if (it.name.contains("insert")) {
                    it.call(InsertArticles::class.objectInstance, *listOf(it.name, host).toTypedArray())
                }
            }
        }
    }
}
