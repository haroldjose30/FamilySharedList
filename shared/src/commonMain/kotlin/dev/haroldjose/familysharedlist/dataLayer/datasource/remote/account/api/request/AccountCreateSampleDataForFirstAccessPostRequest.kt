package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.request

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import kotlinx.serialization.Serializable

data class AccountCreateSampleDataForFirstAccessPostRequest(
    val uuid: String
) : IAJHttpRequest {
    override val urlBase: String
        get() =  BuildKonfig.apiUrl
    override val path: String
        get() = "custom/v1/account/CreateSampleDataForFirstAccess"
    override val method: AJHttpMethod
        get() = AJHttpMethod.POST
    override val headers: AJHttpHeaders?
        get() = mapOf(
            "api-key" to BuildKonfig.apiKey,
        )
    override val queryParameters: AJHttpQueryParameters? = null
    override var body: Any? = null

    init {
        val accountUUid = AccountCreateSampleDataForFirstAccessPostRequestBody(uuid)
        body = accountUUid
    }

    @Serializable
    data class AccountCreateSampleDataForFirstAccessPostRequestBody(
        val uuid: String
    )
}