package com.keygenqt.features.kchat.db.migration

import com.keygenqt.features.kchat.db.models.Chats
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress(
    "unused",
    "ClassName"
)
class V1__create_chats : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {

            SchemaUtils.create(Chats)

            Chats.insert {
                it[userId] = "admin"
                it[name] = "First chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Second chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Third chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Fourth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Fifth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Sixth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Seventh chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Eighth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Ninth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }

            Chats.insert {
                it[userId] = "admin"
                it[name] = "Tenth chat"
                it[dateUpdated] = System.currentTimeMillis()
            }
        }
    }
}
