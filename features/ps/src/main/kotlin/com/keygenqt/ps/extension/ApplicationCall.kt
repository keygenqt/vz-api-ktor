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
package com.keygenqt.ps.extension

import com.keygenqt.core.exceptions.AppException
import com.keygenqt.core.exceptions.md5
import com.keygenqt.ps.base.UserSession
import com.keygenqt.ps.db.models.UserRole
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.sessions.*

/**
 * Check role auth is ADMIN
 */
fun ApplicationCall.checkRoleAdmin() {
    val session = sessions.get<UserSession>()
    if (session == null || session.role != UserRole.ADMIN.name) {
        throw AppException.ErrorForbidden("Action is forbidden, log in with a user who has more rights ;)")
    }
}

/**
 * Check is not auth user
 */
fun ApplicationCall.isNotAuth() = sessions.get<UserSession>() == null

/**
 * Get unique connect
 */
fun ApplicationCall.connectKey() = request.host().md5()
