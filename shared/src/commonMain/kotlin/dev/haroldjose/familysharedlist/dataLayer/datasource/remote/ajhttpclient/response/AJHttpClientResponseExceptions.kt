package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse

class AJHttpClientResponseGenericException(
    response: HttpResponse,
    cachedResponseText: String,
    httpFailureReason: String
) : ResponseException(response, cachedResponseText) {
    override val message: String = httpFailureReason
}

class AJHttpClientResponseUnknownException(
    override val message: String,
    override val cause: Throwable?
): Error(message, cause)