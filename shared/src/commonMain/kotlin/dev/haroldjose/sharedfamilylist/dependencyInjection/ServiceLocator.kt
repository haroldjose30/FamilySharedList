package dev.haroldjose.sharedfamilylist.dependencyInjection

import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import kotlin.native.concurrent.ThreadLocal



interface IServiceLocator

@ThreadLocal
object ServiceLocator: IServiceLocator {

    val getAllFamilyListUseCase: GetAllFamilyListUseCase by lazy {
        GetAllFamilyListUseCase()
    }

    val createFamilyListUseCase: CreateFamilyListUseCase by lazy {
        CreateFamilyListUseCase()
    }

    val updateFamilyListUseCase: UpdateFamilyListUseCase by lazy {
        UpdateFamilyListUseCase()
    }

    val deleteFamilyListUseCase: DeleteFamilyListUseCase by lazy {
        DeleteFamilyListUseCase()
    }
}

