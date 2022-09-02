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
import com.keygenqt.core.utils.ConstantsMime.toExtension
import com.keygenqt.ps.db.models.Upload
import com.keygenqt.ps.extension.checkRoleAdmin
import com.keygenqt.ps.service.UploadsService
import com.keygenqt.ps.utils.Constants.PATH_UPLOAD
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*

fun Route.uploadRoute() {
    val uploadsService: UploadsService by inject()

    route("/file") {
        get("/uploads") {
            call.respond(
                uploadsService.getAll()
            )
        }
        post("/upload") {
            call.checkRoleAdmin()

            val uploads = mutableListOf<Upload>()
            val multipart = call.receiveMultipart()

            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    println(part.contentType)

                    val upload = Upload(
                        fileName = "${UUID.randomUUID()}.${part.contentType.toExtension()}",
                        fileMime = part.contentType.toString(),
                        originalFileName = part.originalFileName!!
                    )

                    val file = File("$PATH_UPLOAD/${upload.fileName}")

                    part.streamProvider().use { its ->
                        file.outputStream().buffered().use {
                            its.copyTo(it)
                        }
                    }

                    uploads.add(
                        uploadsService.insert(
                            fileName = upload.fileName,
                            fileMime = upload.fileMime,
                            originalFileName = upload.originalFileName
                        )
                    )
                }

                part.dispose()
            }

            if (uploads.isNotEmpty()) {
                if (uploads.size == 1) {
                    call.respond(uploads.first())
                } else {
                    call.respond(call.respond(uploads))
                }
            } else {
                throw AppException.Error400("Error upload file")
            }
        }

        delete("/{name}") {
            call.checkRoleAdmin()

            val name = call.getStringParam()
            // delete db row
            uploadsService.deleteByFileName(name)
            // delete file
            val file = File("$PATH_UPLOAD/$name")
            if (file.exists()) {
                file.delete()
                call.respond(HttpStatusCode.OK)
            } else throw AppException.Error404("File not found")
        }
    }
}
