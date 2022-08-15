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
package com.keygenqt.ps.route.auth

import com.keygenqt.core.exceptions.AppRuntimeException
import com.keygenqt.ps.route.auth.elements.AuthRequest
import com.keygenqt.ps.route.auth.elements.AuthResponse
import com.keygenqt.ps.service.TokensService
import com.keygenqt.ps.service.UsersService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.authRoute() {

    val userService: UsersService by inject()
    val tokenService: TokensService by inject()

    route("/auth") {
        post {
            val auth = call.receive<AuthRequest>()
            val user = userService.findUserByAuth(auth.email, auth.password)

            user?.let {
                val token = user.tokens.firstOrNull { it.deviceId == auth.deviceId }?.token ?: run {
                    tokenService.createToken(user, auth.deviceId).token
                }
                call.respond(
                    AuthResponse(
                        id = user.id,
                        email = user.email,
                        role = user.role,
                        token = token,
                    )
                )
            } ?: throw AppRuntimeException.ErrorAuthorized()
        }
    }
}
