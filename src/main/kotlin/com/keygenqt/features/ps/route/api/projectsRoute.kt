package com.keygenqt.features.ps.route.api

import com.keygenqt.features.ps.service.ProjectsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.projectsRoute() {

    val service: ProjectsService by inject()

    route("/projects") {
        get {
            call.respondText("{project: []}")
//            call.respond(service.getAllBlog())
        }
    }
}