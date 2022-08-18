package com.keygenqt.ps.route.auth.elements

import kotlinx.serialization.Serializable

/**
 * Response success auth
 */
@Serializable
data class LogoutResponse(
    val success: Boolean,
)