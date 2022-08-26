package com.keygenqt.ps.db.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SizedIterable


object GitHubUsers : IntIdTable() {
    val publicReposCount = long("publicReposCount").uniqueIndex()
    val followersCount = long("followersCount").uniqueIndex()
    val createAt = long("createAt")
}

/**
 * Exposed entity
 */
class GitHubUserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GitHubUserEntity>(GitHubUsers)

    var publicReposCount by GitHubUsers.publicReposCount
    var followersCount by GitHubUsers.followersCount
    var createAt by GitHubUsers.createAt
}

@Serializable
data class GitHubUser(
    @SerialName("public_repos") val publicReposCount: Long,
    @SerialName("followers") val followersCount: Long,
    val createAt: Long? = null,
)

/**
 * Convert to model
 */
fun GitHubUserEntity.toGitHubUser() = GitHubUser(
    publicReposCount = publicReposCount,
    followersCount = followersCount,
    createAt = createAt,
)

/**
 * Convert list
 */
fun SizedIterable<GitHubUserEntity>.toGitHubUsers(): List<GitHubUser> {
    return map { it.toGitHubUser() }
}