package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.ArticleUploads
import com.keygenqt.ps.db.models.ProjectUploads
import com.keygenqt.ps.db.models.Uploads
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V5__create_uploads : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Uploads)
            SchemaUtils.create(ArticleUploads)
            SchemaUtils.create(ProjectUploads)
        }
    }
}
