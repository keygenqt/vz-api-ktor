package com.keygenqt.core.exceptions

import io.ktor.util.*
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Create md5 string
 */
fun String?.md5() = this?.toByteArray(Charsets.UTF_8)
    ?.let { MessageDigest.getInstance("MD5").digest(it) }
    ?.let { String.format("%032x", BigInteger(1, it)) }
    ?: ""

/**
 * Create hex md5 string
 */
fun String?.md5Hex() = hex(md5())