package com.keygenqt.features.ps.route.api

import com.keygenqt.features.ps.service.ArticlesService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.articlesRoute() {

    val service: ArticlesService by inject()

    route("/articles") {
        get {
            call.respondText("{articles: []}")
//            call.respond(service.getAllBlog())
        }
    }
}