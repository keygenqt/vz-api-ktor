package com.keygenqt.ps.extension

import com.keygenqt.core.exceptions.AppException
import com.keygenqt.ps.base.UserSession
import com.keygenqt.ps.db.models.UserRole
import io.ktor.server.application.*
import io.ktor.server.sessions.*

/**
 * Get ID from params with validate
 */
fun ApplicationCall.checkRoleAdmin() {
    val session = sessions.get<UserSession>()
    if (session == null || session.role != UserRole.ADMIN.name) {
        throw AppException.ErrorForbidden("Action is forbidden, log in with a user who has more rights ;)")
    }
}