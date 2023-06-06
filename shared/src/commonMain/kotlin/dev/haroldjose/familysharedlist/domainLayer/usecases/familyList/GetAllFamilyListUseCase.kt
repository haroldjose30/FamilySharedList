package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.extensions.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class GetAllFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    @NativeCoroutines
    suspend fun execute(): List<FamilyListModel> {

        return familyListRepository.findAll().map { it.toModel() }
    }
}