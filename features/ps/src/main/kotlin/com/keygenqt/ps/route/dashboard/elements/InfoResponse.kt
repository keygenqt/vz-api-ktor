package com.keygenqt.ps.route.dashboard.elements

import kotlinx.serialization.Serializable


/**
 * Response count
 */
@Serializable
data class InfoResponse(
    val count: Long,
    val data: List<Long> = listOf(),
)