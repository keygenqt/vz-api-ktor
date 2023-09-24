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
package com.keygenqt.ps.route.search

import com.keygenqt.ps.extension.connectKey
import com.keygenqt.ps.route.search.elements.SearchArticle
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch._types.Refresh
import org.opensearch.client.opensearch._types.mapping.*
import org.opensearch.client.opensearch.core.BulkRequest
import org.opensearch.client.opensearch.core.bulk.BulkOperation
import org.opensearch.client.opensearch.core.bulk.IndexOperation
import org.opensearch.client.opensearch.indices.CreateIndexRequest
import org.opensearch.client.opensearch.indices.DeleteIndexRequest
import org.opensearch.client.opensearch.indices.IndexSettings

fun Route.searchRoute() {

    val openSearchClient: OpenSearchClient by inject()
    val articlesService: ArticlesService by inject()

    route("/search") {
        post("/synchronization") {

            // delete index
            openSearchClient.indices().delete { r: DeleteIndexRequest.Builder ->
                r.index(Constants.INDEX_OPENSEARCH)
            }

            // create index
            val settings: IndexSettings = IndexSettings.Builder()
                .numberOfShards("2")
                .numberOfReplicas("1")
                .build()

            val mapping: TypeMapping = TypeMapping.Builder()
                .properties("id", Property.Builder().integer(IntegerNumberProperty.Builder().build()).build())
                .properties("url", Property.Builder().text(TextProperty.Builder().build()).build())
                .properties("title", Property.Builder().text(TextProperty.Builder().build()).build())
                .properties("content", Property.Builder().text(TextProperty.Builder().build()).build())
                .properties("createAt", Property.Builder().date(DateProperty.Builder().build()).build())
                .build()

            val createIndexRequest: CreateIndexRequest = CreateIndexRequest.Builder()
                .index(Constants.INDEX_OPENSEARCH)
                .settings(settings)
                .mappings(mapping)
                .build()

            openSearchClient.indices().create(createIndexRequest)

            // bulk data
            val operations = articlesService.getAllPublic(
                connectKey = call.connectKey(),
            ).map {
                BulkOperation.Builder()
                    .index(
                        IndexOperation.of { io ->
                            io.index(Constants.INDEX_OPENSEARCH).id(it.id?.toString() ?: "0")
                                .document(
                                    SearchArticle(
                                        id = it.id!!,
                                        url = it.url,
                                        title = it.title,
                                        content = it.content,
                                        createAt = it.createAt
                                    )
                                )
                        }
                    )
                    .build()
            }

            openSearchClient.bulk { r: BulkRequest.Builder ->
                r.index(Constants.INDEX_OPENSEARCH)
                    .operations(operations)
                    .refresh(Refresh.WaitFor)
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}
