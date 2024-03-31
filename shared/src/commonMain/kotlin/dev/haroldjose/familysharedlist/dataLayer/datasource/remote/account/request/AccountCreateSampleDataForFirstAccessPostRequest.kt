package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request

import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResourceUrl
import kotlinx.serialization.Serializable

data class AccountCreateSampleDataForFirstAccessPostRequest(
    val uuid: String
) : IAJHttpRequest {
    override val urlBase: String
        get() =  BuildKonfig.apiUrl
    override val path: String
        get() = MongoDbResourceUrl.CREATE_SAMPLE_DATA_FOR_FIRST_ACCESS.value
    override val method: AJHttpMethod
        get() = AJHttpMethod.POST
    override val headers: AJHttpHeaders?
        get() = mapOf(
            "api-key" to BuildKonfig.apiKey,
        )
    override val queryParameters: AJHttpQueryParameters? = null
    override var body: Any? = BodyData(uuid)

    @Serializable
    private data class BodyData(
        val uuid: String
    )
}