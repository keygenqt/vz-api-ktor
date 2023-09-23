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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.keygenqt.core.extension.getStringParam
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier
import org.apache.hc.core5.http.HttpHost
import org.apache.hc.core5.ssl.SSLContextBuilder
import org.opensearch.client.json.jackson.JacksonJsonpMapper
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch._types.query_dsl.MultiMatchQuery
import org.opensearch.client.opensearch._types.query_dsl.Query
import org.opensearch.client.opensearch.core.SearchResponse
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate


@Serializable
data class SearchArticle(
    val title: String,
    val content: String,
    val timestamp: Int,
)

@Throws(NoSuchAlgorithmException::class, KeyStoreException::class, KeyManagementException::class)
fun createOpenSearchClient(): OpenSearchClient {
    val hosts = arrayOf(
        HttpHost("https", "localhost", 9200)
    )
    val sslContext = SSLContextBuilder.create()
        .loadTrustMaterial(
            null
        ) { chains: Array<X509Certificate?>?, authType: String? -> true }
        .build()
    val transport = ApacheHttpClient5TransportBuilder
        .builder(*hosts)
        .setMapper(
            JacksonJsonpMapper(
                jacksonObjectMapper()
            )
        )
        .setHttpClientConfigCallback { httpClientBuilder: HttpAsyncClientBuilder ->
            val credentialsProvider =
                BasicCredentialsProvider()
            for (host in hosts) {
                credentialsProvider.setCredentials(
                    AuthScope(host),
                    UsernamePasswordCredentials("admin", "admin".toCharArray())
                )
            }
            // Disable SSL/TLS verification as our local testing clusters use self-signed certificates
            val tlsStrategy = ClientTlsStrategyBuilder.create()
                .setSslContext(sslContext)
                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build()
            val connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .setTlsStrategy(tlsStrategy)
                .build()
            httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider)
                .setConnectionManager(connectionManager)
        }
        .build()

    return OpenSearchClient(transport)
}

fun Route.guestSearchRoute() {
    val index = "sample-search-articles"
    val client = createOpenSearchClient()

    route("/search") {
        get {
            val searchText = call.getStringParam("value")
            val searchResponse: SearchResponse<SearchArticle> = client.search({ s ->
                s.index(index).query { q: Query.Builder ->
                    q.multiMatch { m: MultiMatchQuery.Builder ->
                        m.fields(SearchArticle::title.name, SearchArticle::content.name).query(searchText)
                    }
                }
            }, SearchArticle::class.java)
            call.respond(searchResponse.hits().hits().map { e -> e.source() })
        }
    }
}
