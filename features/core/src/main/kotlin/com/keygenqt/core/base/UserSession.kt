package com.keygenqt.core.base

/**
 * Session cookie
 */
data class UserSession(
    val role: String,
    val token: String,
    val deviceId: String,
)