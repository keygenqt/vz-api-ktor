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
package com.keygenqt.ps.db.models

import com.keygenqt.core.db.IntSubQueryEntityClass
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Connects : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val isSent = bool("isSent").default(false)
    val locale = varchar("locale", 20)
    val createAt = long("createAt")
    val updateAt = long("updateAt")
}

/**
 * Exposed entity
 */
class ConnectEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntSubQueryEntityClass<ConnectEntity>(Connects)

    var email by Connects.email
    var isSent by Connects.isSent
    var locale by Connects.locale
    var createAt by Connects.createAt
    var updateAt by Connects.updateAt
}

@Serializable
data class Connect(
    val id: Int? = null,
    val email: String,
    val isSent: Boolean,
    val locale: String,
    val createAt: Long,
    val updateAt: Long
)

/**
 * Convert to model
 */
fun ConnectEntity.toConnect() = Connect(
    id = id.value,
    email = email,
    isSent = isSent,
    locale = locale,
    createAt = createAt,
    updateAt = updateAt
)

/**
 * Convert list
 */
fun Iterable<ConnectEntity>.toConnects(): List<Connect> {
    return map { it.toConnect() }
}
