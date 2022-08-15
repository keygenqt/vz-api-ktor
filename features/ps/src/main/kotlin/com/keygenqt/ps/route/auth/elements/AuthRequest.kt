package com.keygenqt.ps.route.auth.elements

import kotlinx.serialization.Serializable

/**
 * Route request auth
 */
@Serializable
data class AuthRequest(
    val email: String,
    val deviceId: String,
    val password: String,
)