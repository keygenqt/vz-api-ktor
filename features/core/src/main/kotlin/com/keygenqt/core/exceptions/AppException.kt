/**
 * Copyright © 2022 Surf. All rights reserved.
 */
package com.keygenqt.core.exceptions

import kotlinx.serialization.Serializable

/**
 *  Serializable model for response error
 */
@Serializable
data class AppException(
    val code: Int,
    val message: String,
)
