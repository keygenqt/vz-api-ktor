package com.keygenqt.core.base

import org.mindrot.jbcrypt.BCrypt
import kotlin.random.Random

object Password {

    /**
     * Create hash for db
     */
    fun encode(password: String) =
        BCrypt.hashpw(password, BCrypt.gensalt())!!

    /**
     * Validate hash from db
     */
    fun validate(password: String?, hash: String) =
        password?.let { BCrypt.checkpw(password, hash) } ?: false

    /**
     * Generate random password
     */
    fun random(size: Int = 8): String {
        val alphabets = ('A'..'Z') + ('a'..'z') + ('0'..'9') + arrayListOf('_', '-')
        val random = Random(System.nanoTime())
        val password = StringBuilder()
        (1..size).forEach { _ ->
            val index = random.nextInt(alphabets.size)
            password.append(alphabets[index])
        }
        return password.toString()
    }
}