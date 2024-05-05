package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class DeleteFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
)  {
    @NativeCoroutines
    suspend fun execute(uuid: String): Boolean {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.DELETE_FAMILY_LIST)
        familyListRepository.delete(
            uuid = uuid
        )
        return true
    }
}