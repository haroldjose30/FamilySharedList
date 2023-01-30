package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.domainLayer.extensions.toModel
import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel

class GetAllFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {

    suspend fun execute(): List<FamilyListModel> {

        return familyListRepository.findAll().map { it.toModel() }
    }
}