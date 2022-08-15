package com.keygenqt.ps.db.migration

import com.keygenqt.ps.db.models.UserRole
import com.keygenqt.ps.db.models.Users
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

@Suppress(
    "unused",
    "ClassName"
)
class V3__create_users : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Users)

            Users.insert {
                it[email] = "keygenqt@gmail.com"
                it[password] = BCrypt.hashpw("12345", BCrypt.gensalt())
                it[role] = UserRole.ADMIN
            }
        }
    }
}
