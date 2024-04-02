package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class CreateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {
    suspend fun execute(item: FamilyListModel) {

        return familyListRepository.insert(
            item = item.toDto()
        )
    }

    suspend fun execute(items: List<FamilyListModel>) {

        return familyListRepository.insert(
            items = items.map { it.toDto() }
        )
    }
}