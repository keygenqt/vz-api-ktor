package com.keygenqt.features.ps.base

import com.keygenqt.base.ConfiguratorApp
import com.keygenqt.base.DatabaseMysql
import com.keygenqt.features.ps.route.api.articlesRoute
import com.keygenqt.features.ps.route.api.projectsRoute
import com.keygenqt.features.ps.route.docs.indexRoute
import com.keygenqt.features.ps.service.ArticlesService
import com.keygenqt.features.ps.service.ProjectsService
import com.keygenqt.features.ps.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfiguratorPS : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql

    override fun Route.routing() {
        route("/${Constants.BASE_API_PATH}") {
            // docs
            indexRoute()
            // api
            articlesRoute()
            projectsRoute()
        }
    }

    override fun di(): Array<Module> {
        return arrayOf(
            module {
                single { ArticlesService(db) }
                single { ProjectsService(db) }
            }
        )
    }

    override fun Application.configure() {
        db = DatabaseMysql(
            jdbc = environment.config.property("${Constants.DBCONFIG_CONFIG}.jdbc").getString(),
            config = environment.config.property("${Constants.DBCONFIG_CONFIG}.config").getString(),
            migration = environment.config.property("${Constants.DBCONFIG_CONFIG}.migration").getString()
        )
    }
}