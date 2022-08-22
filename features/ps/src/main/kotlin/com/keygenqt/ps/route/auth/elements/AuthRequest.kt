package com.keygenqt.ps.route.auth.elements

import com.keygenqt.core.validators.NotNullNotBlank
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable

/**
 * Route request auth
 */
@Serializable
data class AuthRequest(

    @field:NotNullNotBlank
    @field:Email(message = "Must be a valid email")
    val email: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 8, max = 12, message = "Size must be between 8 and 12")
    val password: String? = null,

    @field:NotNullNotBlank
    val deviceId: String? = null,
)