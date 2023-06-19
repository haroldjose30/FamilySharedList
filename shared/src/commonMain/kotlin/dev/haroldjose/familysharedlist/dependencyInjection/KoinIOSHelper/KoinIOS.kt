package dev.haroldjose.familysharedlist.dependencyInjection.KoinIOSHelper

import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import org.koin.core.Koin

val Koin.createFamilyListUseCase: CreateFamilyListUseCase
    get() = get()

val Koin.updateFamilyListUseCase: UpdateFamilyListUseCase
    get() = get()

val Koin.getAllFamilyListUseCase: GetAllFamilyListUseCase
    get() = get()

val Koin.deleteFamilyListUseCase: DeleteFamilyListUseCase
    get() = get()

val Koin.getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
    get() = get()
