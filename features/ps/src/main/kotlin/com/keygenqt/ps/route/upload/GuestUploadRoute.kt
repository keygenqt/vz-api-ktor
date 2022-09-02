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
package com.keygenqt.ps.route.upload

import com.keygenqt.core.exceptions.AppException
import com.keygenqt.core.extension.getStringParam
import com.keygenqt.ps.service.UploadsService
import com.keygenqt.ps.utils.Constants.PATH_UPLOAD
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Route.guestUploadRoute() {
    val uploadsService: UploadsService by inject()

    route("/file") {
        get("/{name}") {
            val name = call.getStringParam()

            val upload = uploadsService.getByFileName(name) ?: uploadsService.getByOriginalFileName(name)

            val file = File("$PATH_UPLOAD/${upload?.fileName}")

            if (file.exists()) {
                call.respondFile(file)
            } else throw AppException.Error404("File not found")
        }
    }
}
