package dev.haroldjose.familysharedlist.domainLayer.usecases.product

import dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts.IOpenFoodFactsRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.ProductModel

class GetProductByCodeUseCase(
    private val openFoodFactsRepository: IOpenFoodFactsRepository
)  {

    suspend fun execute(code: String): ProductModel? {
        return openFoodFactsRepository.getProductBy(code = code)?.toModel()
    }
}