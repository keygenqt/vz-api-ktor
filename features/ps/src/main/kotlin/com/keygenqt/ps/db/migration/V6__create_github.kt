package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.GitHubRepos
import com.keygenqt.ps.db.models.GitHubUsers
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V6__create_github : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(GitHubRepos)
            SchemaUtils.create(GitHubUsers)
        }
    }
}
