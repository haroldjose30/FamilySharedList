package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.response

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.response.IAJHttpResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsGetProductByGetResponse(
    @SerialName("code")
    val code: String? = null,
    
    @SerialName("product")
    val product: OpenFoodFactsGetProductByGetResponseProduct? = OpenFoodFactsGetProductByGetResponseProduct(),
    
    @SerialName("status")
    val status: Int? = null,
    
    @SerialName("status_verbose")
    val statusVerbose: String? = null
): IAJHttpResponse {
    
    @Serializable
    data class OpenFoodFactsGetProductByGetResponseProduct (
        @SerialName("code")
    val code: String? = null,

        @SerialName("image_front_small_url")
    val imageFrontSmallUrl: String? = null,

        @SerialName("image_front_url")
    val imageFrontUrl: String? = null,

        @SerialName("image_url")
    val imageUrl: String? = null,

        @SerialName("product_name")
    val productName: String? = null
    )
}