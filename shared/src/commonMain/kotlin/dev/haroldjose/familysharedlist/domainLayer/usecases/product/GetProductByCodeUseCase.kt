package dev.haroldjose.familysharedlist.domainLayer.usecases.product

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts.IOpenFoodFactsRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.ProductModel

class GetProductByCodeUseCase(
    private val openFoodFactsRepository: IOpenFoodFactsRepository
)  {
    @NativeCoroutines
    suspend fun execute(code: String): ProductModel? {
        return openFoodFactsRepository.getProductBy(code)?.toModel()
    }
}