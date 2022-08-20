package com.keygenqt.ps.route.articles.elements

import com.keygenqt.core.validators.NotNullNotBlank
import com.keygenqt.ps.db.models.ArticleCategory
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable


/**
 * Route request auth
 */
@Serializable
data class ArticleRequest(
    @field:NotNull(message = "Select category: ANDROID, PC, WEB, IOS, OTHER")
    val category: ArticleCategory? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 255, message = "Size must be between 3 and 255")
    val title: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 500, message = "Size must be between 3 and 500")
    val description: String? = null,

    @field:NotNullNotBlank
    @field:Size(max = 1000, message = "Must be less than or equal to 1000")
    val content: String? = null,
)