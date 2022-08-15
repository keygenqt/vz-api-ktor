package com.keygenqt.core.exceptions

import io.ktor.http.*


/**
 * Error app
 */
sealed class AppRuntimeException(
    open val status: HttpStatusCode,
    open val msg: String? = null,
) : RuntimeException(msg ?: status.description) {

    val exception: AppException by lazy {
        with(status) {
            AppException(
                code = hashCode(),
                message = msg ?: description
            )
        }
    }

    /**
     * Error 404
     */
    data class Error404(
        override val msg: String? = null,
        override val status: HttpStatusCode = HttpStatusCode.NotFound,
    ) : AppRuntimeException(status, msg)


    /**
     * Error expired token in 1 day
     */
    data class TokenExpired(
        override val status: HttpStatusCode = HttpStatusCode.Unauthorized
    ) : AppRuntimeException(status)


    /**
     * Error 500
     */
    data class Error500(
        override val status: HttpStatusCode = HttpStatusCode.InternalServerError
    ) : AppRuntimeException(status)
}