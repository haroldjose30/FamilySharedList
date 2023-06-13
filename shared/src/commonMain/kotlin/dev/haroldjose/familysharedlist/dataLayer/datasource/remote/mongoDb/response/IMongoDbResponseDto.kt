package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response

import kotlinx.serialization.Serializable

@Serializable
data class MongoDbFindAllResponseDto<T>(
    val documents: List<T>
)

@Serializable
data class MongoDbFindByUuidResponseDto<T>(
    val document: T
)