package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.ArticleUploads
import com.keygenqt.ps.db.models.ProjectUploads
import com.keygenqt.ps.db.models.Uploads
import com.keygenqt.ps.utils.Constants
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.system.exitProcess

@Suppress(
    "unused",
    "ClassName"
)
class V0__create_uploads : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Uploads)
            SchemaUtils.create(ArticleUploads)
            SchemaUtils.create(ProjectUploads)

            val uploadDir = File(Constants.PATH_UPLOAD)

            // mkdir
            if (!uploadDir.exists()) {
                val path = mutableListOf<String>()
                Constants.PATH_UPLOAD.split(File.separator).forEach {
                    path.add(it)
                    File(path.joinToString(File.separator)).mkdir()
                }
            }
        }
    }
}
