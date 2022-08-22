package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.Tokens
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V3__create_tokens : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Tokens)
        }
    }
}
