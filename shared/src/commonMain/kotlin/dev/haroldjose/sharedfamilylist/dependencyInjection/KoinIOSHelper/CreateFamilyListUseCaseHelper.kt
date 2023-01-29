package dev.haroldjose.sharedfamilylist.dependencyInjection.KoinIOSHelper

import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel
import dev.haroldjose.sharedfamilylist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateFamilyListUseCaseHelper : KoinComponent {
    private val usecase: CreateFamilyListUseCase by inject()
    suspend fun execute(item: FamilyListModel)  = usecase.execute(item)
}