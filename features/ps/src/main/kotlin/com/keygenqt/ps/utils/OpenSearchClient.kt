package com.keygenqt.ps.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate

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
