package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.VideoUploads
import com.keygenqt.ps.db.models.Videos
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V9__create_videos : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Videos)
            SchemaUtils.create(VideoUploads)
        }
    }
}
