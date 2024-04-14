package dev.haroldjose.familysharedlist.dataLayer.dto

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FamilyListDto(
    override val uuid: String = "",
    val name: String = "",
    val isCompleted: Boolean = false,
    var isCompletedDate: LocalDateTime? = null,
    var isPrioritized: Boolean = false,
    var quantity: Int = 1,
    var price: Double = 0.0,
    var product: ProductDto? = null,
): IMongoDbBaseDto