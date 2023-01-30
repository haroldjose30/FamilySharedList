package dev.haroldjose.sharedfamilylist.dependencyInjection.KoinIOSHelper

import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import org.koin.core.Koin

val Koin.createFamilyListUseCase: CreateFamilyListUseCase
    get() = get()

val Koin.updateFamilyListUseCase: UpdateFamilyListUseCase
    get() = get()

val Koin.getAllFamilyListUseCase: GetAllFamilyListUseCase
    get() = get()

val Koin.deleteFamilyListUseCase: DeleteFamilyListUseCase
    get() = get()