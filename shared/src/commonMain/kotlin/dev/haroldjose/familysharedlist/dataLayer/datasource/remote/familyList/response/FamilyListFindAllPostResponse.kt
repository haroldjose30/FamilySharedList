package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import kotlinx.serialization.Serializable

@Serializable
data class FamilyListFindAllPostResponse(
    val documents: List<FamilyListDto>
): IAJHttpResponse