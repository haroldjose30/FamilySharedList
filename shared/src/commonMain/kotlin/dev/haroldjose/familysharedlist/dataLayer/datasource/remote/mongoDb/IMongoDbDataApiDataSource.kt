package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import kotlinx.serialization.modules.SerializersModule

interface IMongoDbDataApiDataSource<T: IMongoDbBaseDto> {

    val dataSource: String
    var database: String
    val collection: String
    val serializers: SerializersModule

    suspend fun insert(item: T)
    suspend fun findAll(): List<T>
    suspend fun findBy(uuid: String): T?
    suspend fun update(item: T)
    suspend fun delete(uuid: String)
    fun setDataBase(databaseName: String)
}