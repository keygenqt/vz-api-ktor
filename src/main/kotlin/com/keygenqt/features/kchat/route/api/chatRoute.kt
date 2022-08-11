package com.keygenqt.features.kchat.route.api

import com.keygenqt.features.kchat.service.ChatService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.chatRoute() {

    val service: ChatService by inject()

    route("/chat") {
        get {
            call.respondText("{chat: []}")
//            call.respond(service.getAllBlog())
        }
    }
}