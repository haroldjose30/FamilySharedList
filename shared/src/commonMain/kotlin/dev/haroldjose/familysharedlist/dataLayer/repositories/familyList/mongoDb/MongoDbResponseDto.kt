package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.mongoDb

import kotlinx.serialization.Serializable

@Serializable
internal data class MongoDbResponseDto<T> (
    val documents: T
)