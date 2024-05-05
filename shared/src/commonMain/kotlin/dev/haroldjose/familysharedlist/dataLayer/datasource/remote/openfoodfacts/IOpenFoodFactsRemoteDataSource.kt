package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.response.OpenFoodFactsGetProductByGetResponse

interface IOpenFoodFactsRemoteDataSource {
    suspend fun getProductBy(
        code: String
    ): OpenFoodFactsGetProductByGetResponse?
}