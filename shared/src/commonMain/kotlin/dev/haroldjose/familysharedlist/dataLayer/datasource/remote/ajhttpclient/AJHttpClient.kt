package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient

import IAJHttpResponse
import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AJHttpClient {

    //TODO: handle Error in all request
    //TODO: implement DI
    val client = HttpClient() {
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
    }

     suspend inline fun <reified T:IAJHttpResponse> send(request: IAJHttpRequest): T? {

        val httpStatement: HttpStatement = client.prepareRequest(urlString = request.urlBase+request.path) {
            method = when (request.method) {
                AJHttpMethod.GET -> HttpMethod.Get
                AJHttpMethod.POST -> HttpMethod.Post
                AJHttpMethod.PUT -> HttpMethod.Put
                AJHttpMethod.DELETE -> HttpMethod.Delete
            }
            url {
                request.queryParameters?.forEach {
                    parameters.append(it.key,it.value)
                }
                request.headers?.forEach {
                    headers.append(it.key,it.value)
                }
            }
            request.body?.let {
                contentType(ContentType.Application.Json)
                setBody(it)
            }
        }

         val httpResponse = httpStatement.execute()

        if (httpResponse.status.value in 200..299) {
            return httpResponse.body<T>()
        }

        return null
    }
}