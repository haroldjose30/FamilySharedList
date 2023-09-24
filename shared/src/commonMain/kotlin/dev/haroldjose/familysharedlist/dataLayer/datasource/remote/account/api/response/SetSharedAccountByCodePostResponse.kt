package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class SetSharedAccountByCodePostResponse(
    val result: Boolean
): IAJHttpResponse