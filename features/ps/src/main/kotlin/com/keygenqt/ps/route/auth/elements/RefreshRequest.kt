package com.keygenqt.ps.route.auth.elements

import kotlinx.serialization.Serializable

/**
 * Route request auth
 */
@Serializable
data class RefreshRequest(
    val deviceId: String,
    val refreshToken: String,
)