package com.keygenqt.core.extension

import com.keygenqt.core.exceptions.AppException
import io.ktor.server.application.*
import io.ktor.server.request.*
import jakarta.validation.Validation

/**
 * Get request with validate
 */
suspend inline fun <reified T : Any> ApplicationCall.receiveValidate(): T {

    val request = try {
        receive<T>()
    } catch (ex: Exception) {
        throw AppException.Error400(ex.message)
    }

    val validate = Validation.buildDefaultValidatorFactory().validator.validate(request)

    if (validate.isEmpty()) {
        return request
    } else {
        throw AppException.Error422(validate)
    }
}

/**
 * Get ID from params with validate
 */
fun ApplicationCall.getId(): Int = parameters["id"]
    ?.toIntOrNull() ?: throw AppException.Error404("Must provide ID number")