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
package com.keygenqt.ps.base

import com.keygenqt.core.ConfiguratorApp
import com.keygenqt.core.DatabaseMysql
import com.keygenqt.ps.route.api.articlesRoute
import com.keygenqt.ps.route.api.projectsRoute
import com.keygenqt.ps.route.docs.indexRoute
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.service.ProjectsService
import com.keygenqt.ps.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.core.module.Module
import org.koin.dsl.module

class ConfiguratorPS : ConfiguratorApp() {

    private lateinit var db: DatabaseMysql

    override fun Route.routing() {
        route("/${Constants.BASE_API_PATH}") {
            // docs
            indexRoute()
            // api
            articlesRoute()
            projectsRoute()
        }
    }

    override fun di(): Array<Module> {
        return arrayOf(
            module {
                single { ArticlesService(db) }
                single { ProjectsService(db) }
            }
        )
    }

    override fun Application.configure() {
        db = DatabaseMysql(
            jdbc = environment.config.property("${Constants.DBCONFIG_CONFIG}.jdbc").getString(),
            config = environment.config.property("${Constants.DBCONFIG_CONFIG}.config").getString(),
            migration = environment.config.property("${Constants.DBCONFIG_CONFIG}.migration").getString()
        )
    }
}
