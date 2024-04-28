package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AJHttpClient {

    val client = HttpClient() {
        expectSuccess = true
//        HttpResponseValidator {
//            handleResponseExceptionWithRequest { exception, request ->
//                (exception as? ClientRequestException)?.let {
//                    val exceptionResponse = it.response
//
//                    throw AJHttpClientResponseGenericException(
//                        response = exceptionResponse,
//                        cachedResponseText = exceptionResponse.bodyAsText(),
//                        httpFailureReason = HttpStatusCode.fromValue(exceptionResponse.status.value).description,
//                    )
//                }
//
//                throw AJHttpClientResponseUnknownException(
//                    message = exception.message ?: "Unknown error",
//                    cause = exception
//                )
//            }
//        }
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

    suspend inline fun <reified T: IAJHttpResponse> send(request: IAJHttpRequest): T? {
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