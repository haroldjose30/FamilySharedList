package dev.haroldjose.familysharedlist.dependencyInjection.KoinIOSHelper

import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.product.GetProductByCodeUseCase
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.ISettingsSharedViewModel
import org.koin.core.Koin


//FAMILYLIST
val Koin.createFamilyListUseCase: CreateFamilyListUseCase
    get() = get()

val Koin.updateFamilyListUseCase: UpdateFamilyListUseCase
    get() = get()

val Koin.getAllFamilyListUseCase: GetAllFamilyListUseCase
    get() = get()

val Koin.deleteFamilyListUseCase: DeleteFamilyListUseCase
    get() = get()


//ACCOUNT
val Koin.getAccountUseCase: GetAccountUseCase
    get() = get()
val Koin.getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase
    get() = get()
val Koin.getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
    get() = get()
val Koin.setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase
    get() = get()

//Product
val Koin.getProductByCodeUseCase: GetProductByCodeUseCase
    get() = get()

//PRESENTATION
val Koin.iSettingsSharedViewModel: ISettingsSharedViewModel
    get() = get()


