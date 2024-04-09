package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList

import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpClient
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListDeletePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListFindAllPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListFindByPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListInsertManyPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListInsertPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.request.FamilyListUpdatePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListDeletePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListFindAllPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListFindByPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListInsertManyPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListInsertPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList.response.FamilyListUpdatePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

class FamilyListRemoteDataSource(override var database: String = MongoDbResources.Database.DEMO.value): IFamilyListRemoteDataSource {
    //todo: add DI
    private val client: AJHttpClient = AJHttpClient()
    override suspend fun insert(item: FamilyListDto) {
        val request = FamilyListInsertPostRequest(database, item)
        val response = client.send<FamilyListInsertPostResponse>(request)
        Logger.d("FamilyListRemoteDataSource.insert.insertedId",response?.insertedId ?: "")
    }

    override suspend fun insert(items: List<FamilyListDto>) {
        val request = FamilyListInsertManyPostRequest(database, items)
        val response = client.send<FamilyListInsertManyPostResponse>(request)
        response?.insertedIds?.forEach {
            Logger.d("FamilyListRemoteDataSource.insert.insertedId",it)
        }
    }

    override suspend fun findAll(): List<FamilyListDto> {
        val request =  FamilyListFindAllPostRequest(database)
        val response = client.send<FamilyListFindAllPostResponse>(request)
        return response?.documents ?: emptyList()
    }

    override suspend fun findBy(uuid: String): FamilyListDto? {
        val request = FamilyListFindByPostRequest(
            database = database,
            uuid = uuid
        )
        val response = client.send<FamilyListFindByPostResponse>(request)
        return response?.document
    }

    override suspend fun update(item: FamilyListDto) {
        val request = FamilyListUpdatePostRequest(
            database = database,
            dto = item
        )
        val response = client.send<FamilyListUpdatePostResponse>(request)
        Logger.d("FamilyListRemoteDataSource.update.matchedCount",
            response?.matchedCount.toString()
        )
        Logger.d("FamilyListRemoteDataSource.update.modifiedCount",
            response?.modifiedCount.toString()
        )
    }

    override suspend fun delete(uuid: String) {
        val request = FamilyListDeletePostRequest(
            database = database,
            uuid = uuid
        )
        val response = client.send<FamilyListDeletePostResponse>(request)
        Logger.d("FamilyListRemoteDataSource.delete.deletedCount",
            response?.deletedCount.toString()
        )
    }
}

