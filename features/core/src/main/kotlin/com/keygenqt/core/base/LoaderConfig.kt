package com.keygenqt.core.base

import java.io.File
import java.util.*

object LoaderConfig {

    /**
     * Load file conf
     */
    fun loadProperties(path: String): Properties {
        val prop = Properties()

        try {
            File(path).let { if (it.isFile) it else null }?.inputStream()?.use {
                prop.load(it)
            } ?: run {
                this.javaClass.getResourceAsStream(path).use {
                    prop.load(it)
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException("Failed to read property file", ex)
        }

        return prop
    }
}