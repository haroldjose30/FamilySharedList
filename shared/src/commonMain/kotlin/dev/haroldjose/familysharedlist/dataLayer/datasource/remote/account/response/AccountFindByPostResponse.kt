package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto
import kotlinx.serialization.Serializable

@Serializable
data class AccountFindByPostResponse(
    val document: AccountDto?
): IAJHttpResponse