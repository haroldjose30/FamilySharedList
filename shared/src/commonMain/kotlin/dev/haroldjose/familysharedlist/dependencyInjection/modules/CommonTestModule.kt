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
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonTestModule = module {

    //singleOf(::FamilyListInMemoryRepository) bind IFamilyListRepository::class
    factoryOf(::CreateFamilyListUseCase)
    factoryOf(::GetAllFamilyListUseCase)
    factoryOf(::UpdateFamilyListUseCase)
    factoryOf(::DeleteFamilyListUseCase)
}