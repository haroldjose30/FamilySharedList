package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import kotlinx.serialization.Serializable

@Serializable
data class FamilyListInsertManyPostResponse(
    val insertedIds: List<String>
): IAJHttpResponse