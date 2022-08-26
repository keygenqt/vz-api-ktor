package com.keygenqt.ps.serializers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * 2021-05-15T19:50:40Z from github api
 */
object DateSerializer : KSerializer<LocalDateTime> {

    override val descriptor = PrimitiveSerialDescriptor("timestamp", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder
        .encodeString(value.toString())

    override fun deserialize(decoder: Decoder) = decoder
        .decodeString()
        .replace("Z", "")
        .toLocalDateTime()
}