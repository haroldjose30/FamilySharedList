package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class CreateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {
    @NativeCoroutines
    suspend fun execute(item: FamilyListModel): Boolean {
        familyListRepository.insert(
            item = item.toDto()
        )
        return true
    }

    @NativeCoroutines
    suspend fun execute(items: List<FamilyListModel>): Boolean {
        familyListRepository.insert(
            items = items.map { it.toDto() }
        )
        return true
    }
}