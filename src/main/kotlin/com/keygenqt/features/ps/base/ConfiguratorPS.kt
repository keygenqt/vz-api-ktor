package com.keygenqt.features.ps.base

import com.keygenqt.base.ConfiguratorApp
import com.keygenqt.features.ps.di.moduleServices
import com.keygenqt.features.ps.route.api.articlesRoute
import com.keygenqt.features.ps.route.api.projectsRoute
import com.keygenqt.features.ps.route.docs.indexRoute
import com.keygenqt.features.ps.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.module.Module

class ConfiguratorPS : ConfiguratorApp() {

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
            moduleServices
        )
    }

    override fun Application.configure() {

    }
}