package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request


import dev.haroldjose.familysharedlist.BuildKonfig
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResourceUrl
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import kotlinx.serialization.Serializable

data class FamilyListUpdatePostRequest(
    val database: String,
    val dto: FamilyListDto
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
    override var body: Any? = BodyData(
        database = database,
        update = dto
    )

    @Serializable
    private data class BodyData(
        val collection: String = MongoDbResources.Collection.MY_LIST.value,
        val database: String,
        val dataSource: String = MongoDbResources.DataSource.FAMILY_SHARED_LIST_BACKEND0.value,
        val filter: Map<String, String>,
        val update: FamilyListDto
    ){
        constructor(database: String, update: FamilyListDto): this(
            database = database,
            filter = mapOf("uuid" to update.uuid),
            update = update
        )
    }
}