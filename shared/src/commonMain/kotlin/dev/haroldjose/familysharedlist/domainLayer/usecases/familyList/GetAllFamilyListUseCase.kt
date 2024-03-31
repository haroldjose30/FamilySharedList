package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class GetAllFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    suspend fun execute(): List<FamilyListModel> {

        return familyListRepository.findAll().map { it.toModel() }
    }
}