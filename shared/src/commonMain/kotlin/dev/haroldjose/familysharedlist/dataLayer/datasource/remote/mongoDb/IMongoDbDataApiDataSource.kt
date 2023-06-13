package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import io.ktor.client.statement.HttpResponse

interface IMongoDbDataApiDataSource<T: IMongoDbBaseDto> {

    val dataSource: String
    val database: String
    val collection: String

    suspend fun insert(item: T)
    suspend fun findAll(): HttpResponse
    suspend fun findBy(uuid: String): HttpResponse
    suspend fun update(item: T)
    suspend fun delete(uuid: String)
}