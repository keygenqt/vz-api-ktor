package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.Projects
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
                it[title] = "Autoway"
                it[publicImage] = "https://keygenqt.com/images/blog/61337b11b1300.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "KChat (client)"
                it[publicImage] = "https://keygenqt.com/images/blog/612a101db4c35.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "KChat (server)"
                it[publicImage] = "https://keygenqt.com/images/blog/612a101269cee.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Demo Compose РИВ ГОШ"
                it[publicImage] = "https://keygenqt.com/images/blog/612a0cce4ed06.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Firebase Stack"
                it[publicImage] = "https://keygenqt.com/images/blog/612a0b5e47e6d.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Exploring"
                it[publicImage] = "https://keygenqt.com/images/blog/612a08ad79afd.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Auto App Distribution"
                it[publicImage] = "https://keygenqt.com/images/blog/612a076507afe.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "GitHub Viewer"
                it[publicImage] = "https://keygenqt.com/images/blog/60b65ff92ff7d.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "BackupZ2"
                it[publicImage] = "https://keygenqt.com/images/blog/6011ce8e90e52.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Changeln"
                it[publicImage] = "https://keygenqt.com/images/blog/600fd9d1bdc78.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "Screener 2.0"
                it[publicImage] = "https://keygenqt.com/images/blog/60052a6bc4a2f.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "MyLibrary"
                it[publicImage] = "https://keygenqt.com/images/blog/5fb4be5080358.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "NumberMask"
                it[publicImage] = "https://keygenqt.com/images/blog/5ed615b3c5d75.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }

            Projects.insert {
                it[title] = "FancyBox3"
                it[publicImage] = "https://keygenqt.com/images/blog/5ed35103d38e7.jpg"
                it[description] = "Description"
                it[createAt] = System.currentTimeMillis()
                it[updateAt] = System.currentTimeMillis()
            }
        }
    }
}
