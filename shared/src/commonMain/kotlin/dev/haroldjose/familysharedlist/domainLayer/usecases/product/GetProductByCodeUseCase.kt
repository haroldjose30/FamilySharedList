package dev.haroldjose.familysharedlist.domainLayer.usecases.product

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts.IOpenFoodFactsRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.ProductModel
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class GetProductByCodeUseCase(
    private val openFoodFactsRepository: IOpenFoodFactsRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
)  {
    @NativeCoroutines
    suspend fun execute(code: String): ProductModel? {
        firebaseAnalytics.logEvent(
            IFirebaseAnalytics.Event.GET_PRODUCT_BY_CODE,
            mapOf(IFirebaseAnalytics.Param.BARCODE to code)
        )
        return openFoodFactsRepository.getProductBy(code)?.toModel()
    }
}