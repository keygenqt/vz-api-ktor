package com.keygenqt.ps.route.auth.elements

import com.keygenqt.ps.db.models.UserRole
import kotlinx.serialization.Serializable

/**
 * Response success auth
 */
@Serializable
data class AuthJWTResponse(
    val id: Int,
    val email: String,
    val role: UserRole,
    val token: String,
    val refreshToken: String,
)