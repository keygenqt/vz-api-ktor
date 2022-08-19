package com.keygenqt.ps.route.articles.elements

import com.keygenqt.ps.db.models.ArticleCategory
import kotlinx.serialization.Serializable

/**
 * Route request auth
 */
@Serializable
data class ArticleRequest(
    val category: ArticleCategory,
    val title: String,
    val description: String,
    val content: String,
)