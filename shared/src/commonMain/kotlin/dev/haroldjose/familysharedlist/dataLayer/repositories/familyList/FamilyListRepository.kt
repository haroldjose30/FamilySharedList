package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familiList.FamilyListMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familiList.IFamilyListMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

internal class FamilyListRepository() : IFamilyListRepository {

    //TODO: add to DI
    val remoteDataSource: IFamilyListMongoDbDataApiDataSource = FamilyListMongoDbDataApiDataSource()

    override suspend fun insert(item: FamilyListDto) {
        remoteDataSource.insert(item)
    }

    override suspend fun findAll(): List<FamilyListDto> {
        return remoteDataSource.findAll()
    }

    override suspend fun update(item: FamilyListDto) {
        remoteDataSource.update(item)
    }

    override suspend fun delete(uuid: String) {
        remoteDataSource.delete(uuid)
    }
}