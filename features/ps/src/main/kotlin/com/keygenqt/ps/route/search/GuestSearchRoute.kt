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

import com.keygenqt.core.extension.getStringParam
import com.keygenqt.ps.extension.connectKey
import com.keygenqt.ps.route.search.elements.SearchArticle
import com.keygenqt.ps.service.ArticlesService
import com.keygenqt.ps.utils.Constants.INDEX_OPENSEARCH
import com.keygenqt.ps.utils.createOpenSearchClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch._types.Refresh
import org.opensearch.client.opensearch._types.mapping.DateProperty
import org.opensearch.client.opensearch._types.mapping.IntegerNumberProperty
import org.opensearch.client.opensearch._types.mapping.Property
import org.opensearch.client.opensearch._types.mapping.TextProperty
import org.opensearch.client.opensearch._types.mapping.TypeMapping
import org.opensearch.client.opensearch._types.query_dsl.MultiMatchQuery
import org.opensearch.client.opensearch._types.query_dsl.Query
import org.opensearch.client.opensearch.core.BulkRequest
import org.opensearch.client.opensearch.core.SearchResponse
import org.opensearch.client.opensearch.core.bulk.BulkOperation
import org.opensearch.client.opensearch.core.bulk.IndexOperation
import org.opensearch.client.opensearch.indices.CreateIndexRequest
import org.opensearch.client.opensearch.indices.DeleteIndexRequest
import org.opensearch.client.opensearch.indices.IndexSettings


fun Route.guestSearchRoute() {

    val openSearchClient: OpenSearchClient by inject()

    route("/search") {
        get {
            val searchText = call.getStringParam("value")
            val searchResponse: SearchResponse<SearchArticle> = openSearchClient.search({ s ->
                s.index(INDEX_OPENSEARCH).query { q: Query.Builder ->
                    q.multiMatch { m: MultiMatchQuery.Builder ->
                        m.fields(SearchArticle::title.name, SearchArticle::content.name).query(searchText)
                    }
                }
            }, SearchArticle::class.java)
            call.respond(searchResponse.hits().hits().map { e -> e.source() })
        }
    }
}
