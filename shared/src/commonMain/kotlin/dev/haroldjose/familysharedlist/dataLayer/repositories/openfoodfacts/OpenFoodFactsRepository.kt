package dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.IOpenFoodFactsDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.OpenFoodFactsDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.ProductDto
import dev.haroldjose.familysharedlist.dataLayer.dto.mappers.toDto

class OpenFoodFactsRepository: IOpenFoodFactsRepository {
    //TODO: add to DI
    private val openFoodFactsDataSource: IOpenFoodFactsDataSource = OpenFoodFactsDataSource()
    
    override suspend fun getProductBy(code: String): ProductDto? {
        val response = openFoodFactsDataSource.getProductBy(code = code)
        return response?.toDto()
    }
}