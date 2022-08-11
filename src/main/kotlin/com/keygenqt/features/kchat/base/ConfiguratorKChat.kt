package com.keygenqt.features.kchat.base

import com.keygenqt.base.ConfiguratorApp
import com.keygenqt.base.DatabaseMysql
import com.keygenqt.features.kchat.route.api.chatRoute
import com.keygenqt.features.kchat.route.docs.indexRoute
import com.keygenqt.features.kchat.service.ChatService
import com.keygenqt.features.kchat.utils.Constants
import com.keygenqt.features.kchat.utils.Constants.DBCONFIG_CONFIG
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfiguratorKChat : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql

    override fun Route.routing() {
        route("/${Constants.BASE_API_PATH}") {
            // docs
            indexRoute()
            // api
            chatRoute()
        }
    }

    override fun di(): Array<Module> {
        return arrayOf(
            module {
                single { ChatService(db) }
            }
        )
    }

    override fun Application.configure() {
        db = DatabaseMysql(
            jdbc = environment.config.property("$DBCONFIG_CONFIG.jdbc").getString(),
            config = environment.config.property("$DBCONFIG_CONFIG.config").getString(),
            migration = environment.config.property("$DBCONFIG_CONFIG.migration").getString()
        ).connectAndMigrate()
    }
}