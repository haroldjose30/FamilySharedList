package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpClient
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.request.OpenFoodFactsGetProductByGetRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.response.OpenFoodFactsGetProductByGetResponse

class OpenFoodFactsDataSource: IOpenFoodFactsDataSource {
    //todo: add DI
    private val client: AJHttpClient = AJHttpClient()
    override suspend fun getProductBy(code: String): OpenFoodFactsGetProductByGetResponse? {
       
        val request = OpenFoodFactsGetProductByGetRequest(
            code = code
        )
        val response = client.send<OpenFoodFactsGetProductByGetResponse>(request)
        return response
    }
}