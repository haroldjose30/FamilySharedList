package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response

import kotlinx.serialization.Serializable

@Serializable
data class MongoDbResponseDto<T>(
    val documents: T
)