/**
 * Copyright © 2022 Surf. All rights reserved.
 */
package com.keygenqt.core.exceptions

import kotlinx.serialization.Serializable

/**
 *  Serializable model for response error
 */
@Serializable
data class ValidateException(
    override val code: Int,
    override val message: String,
    val validate: List<ValidateViolation>,
) : IDataException

/**
 * Field errors
 */
@Serializable
data class ValidateViolation(
    val filed: String,
    val errors: List<String>,
)