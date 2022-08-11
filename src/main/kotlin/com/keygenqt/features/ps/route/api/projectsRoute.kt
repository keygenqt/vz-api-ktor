package com.keygenqt.features.ps.route.api

import com.keygenqt.features.ps.service.ProjectsService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.projectsRoute() {

    val service: ProjectsService by inject()

    route("/projects") {
        get {
            val search = call.parameters["search"]
            val limit = call.parameters["limit"]?.toIntOrNull() ?: 5
            val offset = call.parameters["offset"]?.toIntOrNull() ?: 0
            call.respond(service.getAllProjects(limit, offset, search))
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provide id")
            val chat = service.getProject(id)
            if (chat == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(chat)
        }
    }
}