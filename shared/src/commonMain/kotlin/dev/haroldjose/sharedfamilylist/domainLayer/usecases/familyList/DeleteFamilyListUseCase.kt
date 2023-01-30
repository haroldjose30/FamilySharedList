package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository

class DeleteFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    suspend fun execute(uuid: String) {

        return familyListRepository.delete(
            uuid = uuid
        )
    }
}