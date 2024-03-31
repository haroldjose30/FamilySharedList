package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository

class DeleteFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    suspend fun execute(uuid: String) {

        return familyListRepository.delete(
            uuid = uuid
        )
    }
}