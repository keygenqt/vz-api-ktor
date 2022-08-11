package com.keygenqt.features.ps.route.docs

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.indexRoute() {
    get {
        call.respondText("Docs ps")
    }
}