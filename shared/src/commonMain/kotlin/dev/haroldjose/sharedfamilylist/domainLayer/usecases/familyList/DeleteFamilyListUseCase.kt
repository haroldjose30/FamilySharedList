package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb.FamilyListMongoDbRepository

class DeleteFamilyListUseCase()  {

    //TODO: implement DI
    private val taskRepository: IFamilyListRepository = FamilyListMongoDbRepository()

    suspend fun execute(uuid: String) {

        return taskRepository.delete(
            uuid = uuid
        )
    }
}