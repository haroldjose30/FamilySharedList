package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb.FamilyListMongoDbRepository
import dev.haroldjose.sharedfamilylist.domainLayer.extensions.toModel
import dev.haroldjose.sharedfamilylist.domainLayer.models.*

class GetAllFamilyListUseCase()  {

    //TODO: implement DI
    private val taskRepository: IFamilyListRepository = FamilyListMongoDbRepository()

    suspend fun execute(): List<FamilyListModel> {

        return taskRepository.findAll().map { it.toModel() }
    }
}