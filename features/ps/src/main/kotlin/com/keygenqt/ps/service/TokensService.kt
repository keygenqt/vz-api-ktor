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
import com.auth0.jwt.algorithms.Algorithm
import com.keygenqt.core.db.DatabaseMysql
import com.keygenqt.ps.db.models.*
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class TokensService(
    private val db: DatabaseMysql,
    private val jwtSecret: String,
) {
    /**
     * Add new token
     */
    suspend fun createToken(
        user: User,
        deviceId: String,
    ): Token = db.transaction {
        TokenEntity.new {
            this.userId = EntityID(user.id, Users)
            this.deviceId = deviceId
            this.token = JWT.create()
                .withClaim("email", user.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(jwtSecret))
            this.dateUpdated = System.currentTimeMillis()
        }.toToken()
    }
}
