package com.keygenqt.core.base

import java.io.File
import java.util.*

class LoaderConfig(
    val properties: Properties,
) {
    companion object {
        /**
         * Load file conf
         */
        fun loadProperties(path: String): LoaderConfig {
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

            return LoaderConfig(prop)
        }
    }

    /**
     * Get props or return null
     */
    inline fun <reified T> getPropOrNull(key: String): T? {
        return when (T::class) {
            String::class -> properties.getProperty(key)?.toString()
            Boolean::class -> properties.getProperty(key)?.toString()?.toBoolean()
            Int::class -> properties.getProperty(key)?.toString()?.toIntOrNull()
            else -> properties.getProperty(key)?.toString()
        } as? T
    }
}