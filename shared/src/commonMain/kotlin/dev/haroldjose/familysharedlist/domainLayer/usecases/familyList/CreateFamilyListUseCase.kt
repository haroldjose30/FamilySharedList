package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class CreateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
)  {
    @NativeCoroutines
    suspend fun execute(item: FamilyListModel): Boolean {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.CREATE_FAMILY_LIST)
        familyListRepository.insert(
            item = item.toDto()
        )
        return true
    }

    @NativeCoroutines
    suspend fun execute(items: List<FamilyListModel>): Boolean {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.CREATE_FAMILY_LIST)
        familyListRepository.insert(
            items = items.map { it.toDto() }
        )
        return true
    }
}