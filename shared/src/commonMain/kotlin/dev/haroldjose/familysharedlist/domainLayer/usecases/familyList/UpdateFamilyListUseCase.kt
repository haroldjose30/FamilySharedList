package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class UpdateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    suspend fun execute(item: FamilyListModel) {

        return familyListRepository.update(
            item = item.toDto()
        )
    }
}