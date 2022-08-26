package com.keygenqt.core.utils

import com.keygenqt.core.exceptions.AppException

object Utils {

    /**
     * Catch errors
     */
    suspend fun <T> requestAndCatch(
        block: suspend () -> T,
    ): T = try {
        block()
    } catch (e: Exception) {
        println(e.message)
        AppLogger.log.error(e.message)
        throw AppException.ErrorForbidden("GitHub api limit exceeded")
    }
}