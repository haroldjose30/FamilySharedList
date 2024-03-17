package dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts

import dev.haroldjose.familysharedlist.dataLayer.dto.ProductDto

interface IOpenFoodFactsRepository {
    suspend fun getProductBy(
        code: String
    ): ProductDto?
}