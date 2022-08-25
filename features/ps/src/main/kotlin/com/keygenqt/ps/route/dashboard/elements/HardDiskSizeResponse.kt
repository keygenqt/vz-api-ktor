package com.keygenqt.ps.route.dashboard.elements

import kotlinx.serialization.Serializable


/**
 * Response disk size
 */
@Serializable
data class HardDiskSizeResponse(
    val name: String,
    val blocks: Long,
    val available: Long,
    val used: Long,
    val use: Int
)