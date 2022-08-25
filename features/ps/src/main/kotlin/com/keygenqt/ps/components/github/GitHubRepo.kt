package com.keygenqt.ps.components.github

import com.keygenqt.ps.components.serializers.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

/**
 * Data repo github
 */
@Serializable
data class GitHubRepo(
    @Serializable(with = DateSerializer::class)
    val created_at: LocalDateTime,
    val stargazers_count: Long,
    val language: String? = "",
)