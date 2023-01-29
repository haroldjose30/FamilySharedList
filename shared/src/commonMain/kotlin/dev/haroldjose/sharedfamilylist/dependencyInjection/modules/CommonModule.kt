package dev.haroldjose.sharedfamilylist.dependencyInjection.modules


import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList.mongoDb.FamilyListMongoDbRepository
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::FamilyListMongoDbRepository) bind IFamilyListRepository::class
    factoryOf(::CreateFamilyListUseCase)
    factoryOf(::GetAllFamilyListUseCase)
    factoryOf(::UpdateFamilyListUseCase)
    factoryOf(::DeleteFamilyListUseCase)
}

