package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.Articles
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context


@Suppress(
    "unused",
    "ClassName"
)
class V10__update_articles : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        // Add type column
        context
            ?.connection
            ?.prepareStatement("ALTER TABLE ${Articles.tableName} ADD COLUMN type INT DEFAULT 0 AFTER category;")
            ?.use { statement -> statement.execute() }

        // Add url column
        context
            ?.connection
            ?.prepareStatement("ALTER TABLE ${Articles.tableName} ADD COLUMN url VARCHAR(255) DEFAULT '' AFTER type;")
            ?.use { statement -> statement.execute() }
    }
}
