package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdatePostResponse(
    val matchedCount: Int,
    val modifiedCount: Int
): IAJHttpResponse
