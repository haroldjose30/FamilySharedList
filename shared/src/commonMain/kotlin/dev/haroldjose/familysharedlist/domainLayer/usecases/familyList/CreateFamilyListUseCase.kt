package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class CreateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    @NativeCoroutines
    suspend fun execute(item: FamilyListModel) {

        return familyListRepository.insert(
            item = item.toDto()
        )
    }
}