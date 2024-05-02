package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class UpdateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
)  {
    @NativeCoroutines
    suspend fun execute(item: FamilyListModel): Boolean {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.UPDATE_FAMILY_LIST)
        familyListRepository.update(
            item = item.toDto()
        )

        return true
    }
}