package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class GetAllFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
)  {
    @NativeCoroutines
    suspend fun execute(): List<FamilyListModel> {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.GET_ALL_FAMILY_LIST)
        return familyListRepository.findAll().map { it.toModel() }
    }
}