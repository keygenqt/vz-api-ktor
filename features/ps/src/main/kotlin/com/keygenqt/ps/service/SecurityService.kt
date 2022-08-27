/*
 * Copyright 2022 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.ps.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.ps.db.models.*
import io.ktor.server.auth.jwt.*
import java.util.*

class SecurityService(
    private val db: DatabaseMysql,
    secret: String
) {

    private val nameID = Users.id.name
    private val issuer = "ktor.io"
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private fun generateToken(
        userId: Int,
        timestamp: Long
    ) = JWT.create()
        .withSubject("Authentication")
        .withClaim(nameID, userId)
        .withIssuer(issuer)
        .withExpiresAt(Date(timestamp.expiresAt()))
        .sign(algorithm)

    private fun generateRefreshToken(
        userId: Int,
        timestamp: Long
    ) = JWT.create()
        .withSubject("Refresh")
        .withClaim(nameID, userId)
        .withIssuer(issuer)
        .withIssuedAt(Date(timestamp))
        .sign(algorithm)

    fun generateTokenModel(
        userId: Int,
        deviceId: String
    ) = System.currentTimeMillis().let { timestamp ->
        Token(
            userId = userId,
            deviceId = deviceId,
            createAt = timestamp,
            expiresAt = timestamp.expiresAt(),
            token = generateToken(userId, timestamp),
            refreshToken = generateRefreshToken(userId, timestamp)
        )
    }

    suspend fun findValidToken(
        deviceId: String,
        tokens: List<Token>
    ) = System.currentTimeMillis().expiresAt().let { expiresAt ->
        tokens.firstOrNull { it.deviceId == deviceId }?.let {
            if (expiresAt <= it.expiresAt) {
                // check expires token with delete
                it.id?.let { id -> db.transaction { TokenEntity.findById(id)?.delete() } }
                null
            } else {
                // update refresh token
                val result = it.copy(refreshToken = generateRefreshToken(it.userId, System.currentTimeMillis()))
                result.id?.let { id -> db.transaction { TokenEntity.findById(id)?.refreshToken = result.refreshToken } }
                result
            }
        }
    }

    /**
     * Get user by id from claim JWTCredential
     */
    suspend fun findUserByCredential(credential: JWTCredential) = db.transaction {
        findUserByID(credential.payload.getClaim(nameID).asInt())
    }

    /**
     * Get user by id
     */
    suspend fun findUserByID(userId: Int) = db.transaction {
        UserEntity.findById(userId)?.toUser()
    }

    /**
     * Verify refresh token with get user ID
     */
    fun verify(value: String): Int? = verifier.verify(value).getClaim(nameID).asInt()

    /**
     * (24 hours) * 30 days
     */
    private fun Long.expiresAt() = (this + 3_600_000 * 24) * 30
}
