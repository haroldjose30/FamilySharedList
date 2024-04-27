package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.request

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpMethod
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpHeaders
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.AJHttpQueryParameters
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.request.IAJHttpRequest

data class OpenFoodFactsGetProductByGetRequest(
    val code: String
) : IAJHttpRequest {
    override val urlBase: String
        get() {
            return "https://world.openfoodfacts.org/"
//            return if (getPlatform().isDebug)
//                "https://world.openfoodfacts.net/"
//            else
//                "https://world.openfoodfacts.org/"
        }
    override val path: String
        get() = "api/v2/product/$code"
    override val method: AJHttpMethod
        get() = AJHttpMethod.GET
    override val headers: AJHttpHeaders? = null
    override val queryParameters: AJHttpQueryParameters?
        get() = mapOf(
            "fields" to "product_name,code,image_front_small_url,image_front_url,image_url",
        )
    override var body: Any? = null
}