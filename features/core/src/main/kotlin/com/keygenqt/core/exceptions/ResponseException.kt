/**
 * Copyright © 2022 Surf. All rights reserved.
 */
package com.keygenqt.core.exceptions

import kotlinx.serialization.Serializable

/**
 *  Serializable model for response error
 */
@Serializable
data class ResponseException(
    override val code: Int,
    override val message: String,
): IDataException
