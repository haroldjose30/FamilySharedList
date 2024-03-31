package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request


import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResourceUrl
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResources
import kotlinx.serialization.Serializable

data class AccountFindAllPostRequest(override var body: Any? = BodyData()) : IAJHttpRequest {
    override val urlBase: String
        get() =  BuildKonfig.apiUrl
    override val path: String
        get() = MongoDbResourceUrl.FIND.value
    override val method: AJHttpMethod
        get() = AJHttpMethod.POST
    override val headers: AJHttpHeaders
        get() = mapOf(
            "api-key" to BuildKonfig.apiKey,
        )
    override val queryParameters: AJHttpQueryParameters? = null

    @Serializable
    private data class BodyData(
        val collection: String = MongoDbResources.Collection.ACCOUNT.value,
        val database: String = MongoDbResources.Database.ACCOUNT.value,
        val dataSource: String = MongoDbResources.DataSource.FAMILY_SHARED_LIST_BACKEND0.value,
    )
}