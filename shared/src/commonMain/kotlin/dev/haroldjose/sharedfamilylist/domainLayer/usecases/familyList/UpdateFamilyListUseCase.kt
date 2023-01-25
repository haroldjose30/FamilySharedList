package dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb.FamilyListMongoDbRepository
import dev.haroldjose.sharedfamilylist.domainLayer.extensions.toDto
import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel

class UpdateFamilyListUseCase()  {

    //TODO: implement DI
    private val taskRepository: IFamilyListRepository = FamilyListMongoDbRepository()

    suspend fun execute(item: FamilyListModel) {

        return taskRepository.update(
            item = item.toDto()
        )
    }
}