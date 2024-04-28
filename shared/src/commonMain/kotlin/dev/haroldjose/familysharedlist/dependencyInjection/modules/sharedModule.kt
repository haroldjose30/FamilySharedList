package dev.haroldjose.familysharedlist.dependencyInjection.modules

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.AccountRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.IAccountRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpClient
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.FamilyListRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.IFamilyListRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.IOpenFoodFactsRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.OpenFoodFactsRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.AccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.FamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts.IOpenFoodFactsRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.openfoodfacts.OpenFoodFactsRepository
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.product.GetProductByCodeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {

    //HttpClient
    singleOf(::AJHttpClient)

    //DataSources
    singleOf(::AccountRemoteDataSource) bind IAccountRemoteDataSource::class
    singleOf(::FamilyListRemoteDataSource) bind IFamilyListRemoteDataSource::class
    singleOf(::OpenFoodFactsRemoteDataSource) bind IOpenFoodFactsRemoteDataSource::class

    //Repositories
    singleOf(::FamilyListRepository) bind IFamilyListRepository::class
    singleOf(::KeyValueStorageRepository) bind IKeyValueStorageRepository::class
    singleOf(::AccountRepository) bind IAccountRepository::class
    singleOf(::OpenFoodFactsRepository) bind IOpenFoodFactsRepository::class

    //FamilyList UseCases
    factoryOf(::CreateFamilyListUseCase)
    factoryOf(::GetAllFamilyListUseCase)
    factoryOf(::UpdateFamilyListUseCase)
    factoryOf(::DeleteFamilyListUseCase)

    //Account UseCases
    factoryOf(::GetAccountUseCase)
    factoryOf(::GetLocalAccountUuidUseCase)
    factoryOf(::GetOrCreateAccountFromLocalUuidUseCase)
    factoryOf(::SetSharedAccountByCodeUseCase)

    //product UseCases
    factoryOf(::GetProductByCodeUseCase)
}

