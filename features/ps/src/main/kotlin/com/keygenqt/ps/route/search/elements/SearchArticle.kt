package com.keygenqt.ps.route.search.elements

import kotlinx.serialization.Serializable

@Serializable
data class SearchArticle(
    val id: Int,
    val url: String,
    val title: String,
    val content: String,
    val createAt: Long,
)
