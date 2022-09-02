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
package com.keygenqt.ps.db.inserts

import com.keygenqt.ps.db.models.ProjectCategory
import com.keygenqt.ps.db.models.ProjectEntity
import com.keygenqt.ps.extension.createFileUploadProject
import kotlinx.datetime.toInstant
import org.jetbrains.exposed.sql.SizedCollection

object InsertProjects {

    /**
     * My first personal site
     */
    fun insertPersonalSite1(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2020-03-08T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2020-03-08T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.WEB
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = "https://keygenqt.com/"
            this.urlGitHub = ""
            this.urlSnapcraft = ""
            this.urlDownload = ""
            this.urlYouTube = ""

            // content default
            this.title = "Personal Site 1.0"
            this.description = "My first personal site. Keep for history. Written in Yii2, PHP."

            // content ru
            this.titleRu = "Personal Site 1.0"
            this.descriptionRu = "Мой персональный сайт 1.0. Решил сохранить для истории. Написан на Yii2, PHP."
        }
    }

    /**
     * My personal site 2.0
     */
    fun insertPersonalSite2(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2022-07-14T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-07-14T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.WEB
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = "https://.keygenqt.com/"
            this.urlGitHub = "https://github.com/keygenqt/vz-site-react"
            this.urlSnapcraft = ""
            this.urlDownload = ""
            this.urlYouTube = ""

            // content default
            this.title = "Personal Site 2.0"
            this.description = "My personal site 2.0. Open, modern, informative. Written in React, JS."

            // content ru
            this.titleRu = "Personal Site 2.0"
            this.descriptionRu = "Мой личный сайт 2.0. Открыто, современно, информативно. Написано на React, JS."
        }
    }

    /**
     * Adminka
     */
    fun insertAdminka(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2022-07-17T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-07-17T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.WEB
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = "https://adminka.keygenqt.com/"
            this.urlGitHub = "https://github.com/keygenqt/vz-site-admin-react"
            this.urlSnapcraft = ""
            this.urlDownload = ""
            this.urlYouTube = ""

            // content default
            this.title = "Adminka"
            this.description = "My admin panel for all open source projects. Written in React, JS."

            // content ru
            this.titleRu = "Adminka"
            this.descriptionRu = "Моя админка для всех проектов с открытым исходным кодом. Написано на React, JS."
        }
    }

    /**
     * My API
     */
    fun insertMyApi(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2022-07-17T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2022-07-17T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.WEB
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = "https://api.keygenqt.com/"
            this.urlGitHub = "https://github.com/keygenqt/vz-api-ktor"
            this.urlSnapcraft = ""
            this.urlDownload = ""
            this.urlYouTube = ""

            // content default
            this.title = "My API"
            this.description = "My API for all open source projects. Written in Ktor, Kotlin."

            // content ru
            this.titleRu = "My API"
            this.descriptionRu = "Мой API для всех проектов с открытым исходным кодом. Написано на Ktor, Kotlin."
        }
    }

    /**
     * Changeln
     */
    fun insertChangeln(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2021-01-24T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2021-01-24T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.OTHER
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = ""
            this.urlGitHub = "https://github.com/keygenqt/Changeln"
            this.urlSnapcraft = "https://snapcraft.io/changeln"
            this.urlDownload = "https://github.com/keygenqt/ChangelnCLI/raw/master/data/changeln-1.0.6.pyz"
            this.urlYouTube = ""

            // content default
            this.title = "Changeln"
            this.description = "Generate changelog from git to markdown, html, pdf. Written in Click, Python."

            // content ru
            this.titleRu = "Changeln"
            this.descriptionRu = "Генерация журнал изменений с git на markdown, html, pdf. Написано в Click, Python."
        }
    }

    /**
     * Autoway
     */
    fun insertAutoway(
        dirFiles: String,
        host: String
    ) {
        val publicImage = dirFiles.createFileUploadProject("preview-600.png")

        ProjectEntity.new {
            // base
            this.isPublished = true
            this.uploads = SizedCollection(*listOfNotNull(publicImage).toTypedArray())

            // times
            this.createAt = "2021-09-02T00:00:00Z".toInstant().epochSeconds * 1000
            this.updateAt = "2021-09-02T00:00:00Z".toInstant().epochSeconds * 1000

            // info
            this.category = ProjectCategory.OTHER
            this.publicImage = "$host/api/ps/file/${publicImage?.fileName}"

            // urls
            this.url = ""
            this.urlGitHub = "https://github.com/keygenqt/api-Autoway"
            this.urlSnapcraft = ""
            this.urlDownload = "https://github.com/keygenqt/api-Autoway/raw/master/data/release/Autoway-0.0.1-all.jar"
            this.urlYouTube = "https://youtu.be/HHQzB457xmI"

            // content default
            this.title = "Autoway"
            this.description = "Replacing mocks with a full-fledged REST backend. Written in Kotlinx-CLI, Kotlin."

            // content ru
            this.titleRu = "Autoway"
            this.descriptionRu = "Замена моков на автогенериющийся из миграций REST-бэкенд. Написано в Kotlinx-CLI, Kotlin."
        }
    }
}
