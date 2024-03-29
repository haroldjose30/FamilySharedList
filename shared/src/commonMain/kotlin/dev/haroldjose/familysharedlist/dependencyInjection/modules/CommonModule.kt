package dev.haroldjose.familysharedlist.dependencyInjection.modules

import dev.haroldjose.familysharedlist.dataLayer.repositories.account.AccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.FamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepository
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListSharedViewModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.IFamilyListSharedViewModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.ISettingsSharedViewModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.SettingsSharedViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {

    singleOf(::FamilyListRepository) bind IFamilyListRepository::class
    singleOf(::KeyValueStorageRepository) bind IKeyValueStorageRepository::class
    singleOf(::AccountRepository) bind IAccountRepository::class

    //FamilyList
    factoryOf(::CreateFamilyListUseCase)
    factoryOf(::GetAllFamilyListUseCase)
    factoryOf(::UpdateFamilyListUseCase)
    factoryOf(::DeleteFamilyListUseCase)

    //account
    factoryOf(::GetAccountUseCase)
    factoryOf(::GetLocalAccountUuidUseCase)
    factoryOf(::GetOrCreateAccountFromLocalUuidUseCase)
    factoryOf(::SetSharedAccountByCodeUseCase)

    //UI
    factoryOf(::SettingsSharedViewModel) bind ISettingsSharedViewModel::class
    factoryOf(::FamilyListSharedViewModel) bind IFamilyListSharedViewModel::class
}

