package com.keygenqt.ps.route.dashboard.elements

import kotlinx.serialization.Serializable


/**
 * Response types repos
 */
@Serializable
data class InfoTypesResponse(
    val web: List<Long> = listOf(),
    val ios: List<Long> = listOf(),
    val android: List<Long> = listOf(),
    val other: List<Long> = listOf(),
)