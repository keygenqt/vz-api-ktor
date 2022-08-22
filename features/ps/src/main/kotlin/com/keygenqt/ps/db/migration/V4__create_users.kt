package com.keygenqt.ps.db.migration

import com.keygenqt.core.base.Password
import com.keygenqt.ps.db.models.UserRole
import com.keygenqt.ps.db.models.Users
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V4__create_users : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Users)

            Users.insert {
                it[email] = "guest@keygenqt.com"
                it[password] = Password.encode("12345678")
                it[role] = UserRole.GUEST
            }

            Password.random().let { pass ->
                Users.insert {
                    it[email] = "admin@keygenqt.com"
                    it[password] = Password.encode(pass)
                    it[role] = UserRole.ADMIN
                }

                println("Create new ADMIN, email: admin@keygenqt.com, password: $pass")
            }
        }
    }
}
