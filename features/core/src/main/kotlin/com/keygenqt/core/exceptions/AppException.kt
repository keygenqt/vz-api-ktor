package com.keygenqt.core.exceptions

import com.keygenqt.core.extension.genException
import io.ktor.http.*
import jakarta.validation.ConstraintViolation


/**
 * Errors app
 */
sealed class AppException(
    open val status: HttpStatusCode,
    open val exception: IDataException,
) : RuntimeException(exception.message) {

    /**
     * Error authorized
     */
    data class ErrorAuthorized(
        override val status: HttpStatusCode = HttpStatusCode.Unauthorized,
    ) : AppException(status, status.genException())

    /**
     * Error json parse
     */
    data class JsonDecodingException(
        val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.BadRequest,
    ) : AppException(status, status.genException(msg))

    /**
     * Error 500
     */
    data class Error500(
        override val status: HttpStatusCode = HttpStatusCode.InternalServerError,
    ) : AppException(status, status.genException())

    /**
     * Error 404
     */
    data class Error404(
        val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.NotFound,
    ) : AppException(status, status.genException(msg))

    /**
     * Error 400
     */
    data class Error400(
        val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.BadRequest,
    ) : AppException(status, status.genException(msg))

    /**
     * Error 422
     */
    data class Error422(
        val violation: Set<ConstraintViolation<*>>,
        override val status: HttpStatusCode = HttpStatusCode.UnprocessableEntity,
    ) : AppException(status, status.genException(null, violation))
}