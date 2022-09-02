/*
 * Copyright 2022 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.keygenqt.ps.route.articles.elements

import com.keygenqt.core.validators.NotBlank
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
    val id: Int? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 255, message = "Size must be between 3 and 255")
    val title: String? = null,

    @field:NotNullNotBlank
    @field:Size(min = 3, max = 500, message = "Size must be between 3 and 500")
    val description: String? = null,

    @field:NotNullNotBlank
    @field:Size(max = 10000, message = "Must be less than or equal to 10000")
    val content: String? = null,

    @field:NotBlank(message = "Must not be blank")
    @field:Size(max = 255, message = "Size must be between 3 and 255")
    val titleRu: String? = null,

    @field:NotBlank(message = "Must not be blank")
    @field:Size(max = 500, message = "Size must be between 3 and 500")
    val descriptionRu: String? = null,

    @field:NotBlank(message = "Must not be blank")
    @field:Size(max = 10000, message = "Must be less than or equal to 10000")
    val contentRu: String? = null,

    @field:NotNull(message = "Select category required")
    val category: ArticleCategory? = null,

    @field:NotNullNotBlank
    @field:URL(message = "Must be a valid URL")
    val listImage: String? = null,

    @field:NotNullNotBlank
    @field:URL(message = "Must be a valid URL")
    val viewImage: String? = null,

    @field:NotNull(message = "Must not be null")
    val isPublished: Boolean? = null,

    val uploads: List<Int> = listOf()
)
