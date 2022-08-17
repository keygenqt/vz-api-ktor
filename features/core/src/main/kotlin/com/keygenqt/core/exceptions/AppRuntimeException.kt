package com.keygenqt.core.exceptions

import io.ktor.http.*

/**
 * Errors app
 */
sealed class AppRuntimeException(
    open val status: HttpStatusCode,
    open val msg: String? = null,
) : RuntimeException(msg ?: status.description) {

    /**
     * Error authorized
     */
    data class ErrorAuthorized(
        override val status: HttpStatusCode = HttpStatusCode.Unauthorized
    ) : AppRuntimeException(status)

    /**
     * Error json parse
     */
    data class JsonDecodingException(
        override val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.BadRequest
    ) : AppRuntimeException(status, msg)

    /**
     * Error 500
     */
    data class Error500(
        override val status: HttpStatusCode = HttpStatusCode.InternalServerError
    ) : AppRuntimeException(status)

    /**
     * Error 404
     */
    data class Error404(
        override val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.NotFound,
    ) : AppRuntimeException(status, msg)

    /**
     * Serializable error response
     */
    val exception: AppException by lazy {
        with(status) {
            AppException(
                code = hashCode(),
                message = msg ?: description
            )
        }
    }
}