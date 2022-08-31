package com.keygenqt.ps.db.migration

import com.keygenqt.core.utils.AppLogger
import com.keygenqt.ps.db.models.ProjectCategory
import com.keygenqt.ps.db.models.ProjectEntity
import com.keygenqt.ps.db.models.Projects
import com.keygenqt.ps.db.models.UploadEntity
import com.keygenqt.ps.utils.Constants
import kotlinx.datetime.toInstant
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.*

@Suppress(
    "unused",
    "ClassName"
)
class V2__create_projects : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Projects)

            val host = if (AppLogger.isDebug) "http://localhost:8085" else "https://api.keygenqt.com"

            personalSite(host)
        }
    }
}

/**
 * Project personal site
 */
private fun personalSite(host: String) {
    getFileRelation("personalSite")?.let { upload ->
        ProjectEntity.new {
            this.category = ProjectCategory.WEB
            this.publicImage = "$host/api/ps/file/${upload.fileName}"
            this.title = "Personal Site"
            this.url = ""
            this.urlGitHub = "https://github.com/keygenqt/vz-site-react"
            this.isPublished = true
            this.description =
                "Personal website project. Also contains API, admin panel for all my open source projects"

            this.uploads = SizedCollection(upload)
            this.createAt = "2022-07-14T19:50:40Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-07-14T19:50:40Z".toInstant().epochSeconds * 1000
        }
    }
}

/**
 * Create upload from data
 */
private fun getFileRelation(
    project: String,
): UploadEntity? {
    val file = File("${Constants.PATH_DATA_MIGRATION}/projects/${project}/list.jpg")
    return if (file.isFile) {
        val upload = UploadEntity.new {
            fileName = "${UUID.randomUUID()}.jpg"
            fileMime = "image/png"
            originalFileName = "migration.jpg"
            createAt = System.currentTimeMillis()
        }
        file.copyTo(File("${Constants.PATH_UPLOAD}/${upload.fileName}"))
        upload
    } else null
}
