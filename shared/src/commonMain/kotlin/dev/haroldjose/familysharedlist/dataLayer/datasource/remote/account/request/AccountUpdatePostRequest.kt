package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request


import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResourceUrl
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto
import kotlinx.serialization.Serializable

data class AccountUpdatePostRequest(
    val dto: AccountDto
) : IAJHttpRequest {
    override val urlBase: String
        get() =  BuildKonfig.apiUrl
    override val path: String
        get() = MongoDbResourceUrl.UPDATE_ONE.value
    override val method: AJHttpMethod
        get() = AJHttpMethod.POST
    override val headers: AJHttpHeaders?
        get() = mapOf(
            "api-key" to BuildKonfig.apiKey,
        )
    override val queryParameters: AJHttpQueryParameters? = null
    override var body: Any? = BodyData(dto)

    @Serializable
    private data class BodyData(
        val collection: String = MongoDbResources.Collection.ACCOUNT.value,
        val database: String = MongoDbResources.Database.ACCOUNT.value,
        val dataSource: String = MongoDbResources.DataSource.FAMILY_SHARED_LIST_BACKEND0.value,
        val filter: Map<String, String>,
        val update: AccountDto
    ){
        constructor(update: AccountDto): this(
            filter = mapOf("uuid" to update.uuid),
            update = update
        )
    }
}