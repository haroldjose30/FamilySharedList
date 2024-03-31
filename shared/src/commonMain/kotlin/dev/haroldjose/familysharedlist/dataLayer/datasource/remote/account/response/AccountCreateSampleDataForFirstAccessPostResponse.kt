package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateSampleDataForFirstAccessPostResponse(
    val dataCreated: Boolean
): IAJHttpResponse