package com.keygenqt.ps.route.secret.elements

import com.keygenqt.ps.db.models.UserRole
import kotlinx.serialization.Serializable

/**
 * Response secret
 */
@Serializable
data class SecretResponse(
    val secret: String,
)