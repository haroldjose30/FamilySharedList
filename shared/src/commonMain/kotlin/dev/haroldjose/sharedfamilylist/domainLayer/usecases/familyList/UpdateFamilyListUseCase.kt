package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.domainLayer.extensions.toDto
import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel

class UpdateFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {


    suspend fun execute(item: FamilyListModel) {

        return familyListRepository.update(
            item = item.toDto()
        )
    }
}