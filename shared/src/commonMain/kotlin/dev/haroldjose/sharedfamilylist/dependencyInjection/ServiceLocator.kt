package dev.haroldjose.sharedfamilylist.dependencyInjection

import dev.haroldjose.sharedfamilylist.GlobalState
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.FamilyListInMemoryRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb.FamilyListMongoDbRepository
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import kotlin.native.concurrent.ThreadLocal



interface IServiceLocator

@ThreadLocal
object ServiceLocator: IServiceLocator {

    val getAllFamilyListUseCase: GetAllFamilyListUseCase by lazy {
        GetAllFamilyListUseCase(familyListRepository)
    }

    val createFamilyListUseCase: CreateFamilyListUseCase by lazy {
        CreateFamilyListUseCase(familyListRepository)
    }

    val updateFamilyListUseCase: UpdateFamilyListUseCase by lazy {
        UpdateFamilyListUseCase(familyListRepository)
    }

    val deleteFamilyListUseCase: DeleteFamilyListUseCase by lazy {
        DeleteFamilyListUseCase(familyListRepository)
    }

    val globalState: GlobalState by lazy {
        GlobalState()
    }

    internal val familyListRepository: IFamilyListRepository by lazy {

        if (GlobalState.isRunningUITests)
            FamilyListInMemoryRepository()
        else
            FamilyListMongoDbRepository()
    }
}

