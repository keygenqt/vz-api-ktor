package com.keygenqt.base

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.module.Module

abstract class ConfiguratorApp {

    abstract fun Route.routing()
    abstract fun di(): Array<Module>
    abstract fun Application.configure()

    fun intiConfigure(app: Application) {
        app.apply { configure() }
    }

    fun initRouting(route: Route) {
        route.apply { routing() }
    }
}