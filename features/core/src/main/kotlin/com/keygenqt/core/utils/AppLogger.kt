package com.keygenqt.core.utils

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.util.logging.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

object AppLogger {

    var isDebug: Boolean = false
        private set

    lateinit var log: Logger
        private set

    fun Application.initAppLogger() {

        // init conf
        with(environment.config) {
            isDebug = propertyOrNull("ktor.development")?.getString().toBoolean()
        }

        // logger db
        (LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger).apply {
            level = if (isDebug) ch.qos.logback.classic.Level.DEBUG else ch.qos.logback.classic.Level.OFF
        }

        // configuration
        install(CallLogging) {
            level = if (isDebug) Level.DEBUG else Level.ERROR
        }

        // save const log
        AppLogger.log = log
    }
}