package com.keygenqt.ps.components.github

import kotlinx.serialization.Serializable

/**
 * Data github user
 */
@Serializable
data class GitHubUser(
    val public_repos: Long,
    val followers: Long,
)