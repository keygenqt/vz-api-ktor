package com.keygenqt.ps.route.articles.elements

import com.keygenqt.core.validators.NotNullNotBlank
import com.keygenqt.ps.db.models.ArticleCategory
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable
import org.hibernate.validator.constraints.URL


/**
 * Route request auth
 */
@Serializable
data class ArticleRequest(
    @field:NotNull(message = "Select category required")
    val category: ArticleCategory? = null,

    @field:NotNullNotBlank
    @field:URL(message = "Must be a valid URL")
    val publicImage: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 255, message = "Size must be between 3 and 255")
    val title: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 500, message = "Size must be between 3 and 500")
    val description: String? = null,

    @field:NotNullNotBlank
    @field:Size(max = 10000, message = "Must be less than or equal to 10000")
    val content: String? = null,

    @field:NotNull(message = "Must not be null")
    val isPublished: Boolean? = null,

    val uploads: List<Int> = listOf(),
)