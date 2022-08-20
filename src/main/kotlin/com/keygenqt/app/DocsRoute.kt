/*
 * Copyright 2022 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.app

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.docsRoute() {
    mkdocsRoute()
    dokkaRoute()
}

private fun Route.mkdocsRoute() {
    static {
        serveResources("site")
        defaultResource("index.html", "site")
    }
}

private fun Route.dokkaRoute(path: String = "api") {
    route("/$path") {
        get {
            call.respondRedirect("/$path/")
        }
    }
    static("/$path/") {
        serveResources(path) { route ->
            if (!route.contains("html") && route.contains("features") && route.last() != '/') {
                respondRedirect("$route/")
            }
        }
        defaultResource("index.html", path)
    }
}

private fun Route.serveResources(
    resourcePackage: String? = null,
    checkPath: suspend ApplicationCall.(String) -> Unit = {},
) {
    get("{static-resources...}/") {
        call.serve(resourcePackage, checkPath)
    }
    get("{static-resources...}") {
        call.serve(resourcePackage, checkPath)
    }
}

private suspend fun ApplicationCall.serve(
    resourcePackage: String? = null,
    checkPath: suspend ApplicationCall.(String) -> Unit,
) {
    val relativePath = parameters.getAll("static-resources")?.joinToString(File.separator) ?: return

    // This is key part. We either resolve some resource or resolve index.html using path from the request
    val content = resolveResource(relativePath, resourcePackage)
        ?: resolveResource("$relativePath/index.html", resourcePackage)

    if (content != null) {
        checkPath.invoke(this, request.uri)
        respond(content)
    }
}