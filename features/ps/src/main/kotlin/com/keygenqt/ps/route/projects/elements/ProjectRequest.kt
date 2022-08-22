package com.keygenqt.ps.route.projects.elements

import com.keygenqt.core.validators.NotNullNotBlank
import com.keygenqt.ps.db.models.ProjectCategory
import com.keygenqt.ps.db.models.ProjectLanguage
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kotlinx.serialization.Serializable
import org.hibernate.validator.constraints.URL


/**
 * Route request auth
 */
@Serializable
data class ProjectRequest(
    @field:NotNull(message = "Select category required")
    val category: ProjectCategory? = null,

    @field:NotNull(message = "Select language required")
    val language: ProjectLanguage? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 255, message = "Size must be between 3 and 255")
    val title: String? = null,

    @field:URL(message = "Must be a valid URL")
    @field:Size(max = 255, message = "Must be less than or equal to 255")
    val url: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 500, message = "Size must be between 3 and 500")
    val description: String? = null,

    @field:NotNull(message = "Must not be null")
    val isPublished: Boolean? = null,
)