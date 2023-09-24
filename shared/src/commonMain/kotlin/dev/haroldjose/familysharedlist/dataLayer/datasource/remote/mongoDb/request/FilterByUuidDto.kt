package dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request

import kotlinx.serialization.Serializable

@Serializable
data class FilterByUuidDto(
    val uuid: String
)