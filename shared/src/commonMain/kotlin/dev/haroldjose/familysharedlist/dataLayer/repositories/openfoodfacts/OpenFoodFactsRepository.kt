package dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.IOpenFoodFactsRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.ProductDto
import dev.haroldjose.familysharedlist.dataLayer.dto.mappers.toDto

class OpenFoodFactsRepository(
    val openFoodFactsDataSource: IOpenFoodFactsRemoteDataSource
): IOpenFoodFactsRepository {
    
    override suspend fun getProductBy(code: String): ProductDto? {
        val response = openFoodFactsDataSource.getProductBy(code = code)
        return response?.toDto()
    }
}