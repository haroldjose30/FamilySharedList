package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResourceUrl
import kotlinx.serialization.Serializable

data class SetSharedAccountByCodePostRequest(
    val accountUuid: String,
    val code: String
) : IAJHttpRequest {
    override val urlBase: String
        get() =  BuildKonfig.apiUrl
    override val path: String
        get() = MongoDbResourceUrl.SET_SHARED_ACCOUNT_BY_CODE.value
    override val method: AJHttpMethod
        get() = AJHttpMethod.POST
    override val headers: AJHttpHeaders?
        get() = mapOf(
            "api-key" to BuildKonfig.apiKey,
        )
    override val queryParameters: AJHttpQueryParameters? = null
    override var body: Any? = BodyData(
        accountUuid = accountUuid,
        code = code
    )

    @Serializable
    private data class BodyData(
        val accountUuid: String,
        val code: String
    )
}