package com.keygenqt.core.extension

import com.keygenqt.core.exceptions.AppException
import io.ktor.server.application.*
import io.ktor.server.request.*
import jakarta.validation.Validation

/**
 * Get request with validate
 */
suspend inline fun <reified T : Any> ApplicationCall.receiveValidate(
    message: String? = null,
): T {

    val request = try {
        receive<T>()
    } catch (ex: Exception) {
        throw AppException.Error400(message ?: ex.message)
    }

    val validate = Validation.buildDefaultValidatorFactory().validator.validate(request)

    if (validate.isEmpty()) {
        return request
    } else {
        throw AppException.Error422(message, validate)
    }
}

/**
 * Get value from params with validate
 */
fun ApplicationCall.getNumberParam(key: String = "id"): Int = parameters[key]
    ?.toIntOrNull() ?: throw AppException.Error404("Must provide $key number")

/**
 * Get value from params with validate
 */
fun ApplicationCall.getStringParam(key: String = "name"): String = parameters[key]
    ?: throw AppException.Error404("Must provide $key")