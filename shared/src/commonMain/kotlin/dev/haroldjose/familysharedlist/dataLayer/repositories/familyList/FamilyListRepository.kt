package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.FamilyListRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.IFamilyListRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepositoryEnum

internal class FamilyListRepository() : IFamilyListRepository {

    //TODO: add to DI
    private val remoteDataSource: IFamilyListRemoteDataSource
    private val keyValueStorageRepository: IKeyValueStorageRepository

    init {
        keyValueStorageRepository = KeyValueStorageRepository()
        remoteDataSource = FamilyListRemoteDataSource(database = getSelectedDataBase())
    }

    override suspend fun insert(item: FamilyListDto) {
        remoteDataSource.insert(item)
    }

    override suspend fun insert(items: List<FamilyListDto>) {
        remoteDataSource.insert(items)
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

    private fun getSelectedDataBase() : String {
        keyValueStorageRepository.getString(KeyValueStorageRepositoryEnum.SELECTED_DATABASE_NAME)?.let {
            return it
        }

        keyValueStorageRepository.getString(KeyValueStorageRepositoryEnum.ACCOUNT_UUID)?.let {
            return it
        }

        return MongoDbResources.Database.DEMO.value
    }
}