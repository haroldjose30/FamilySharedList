package dev.haroldjose.familysharedlist.dataLayer.dto

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import kotlinx.serialization.Serializable

@Serializable
data class FamilyListDto(
    override val uuid: String = "",
    val name: String = "",
    val isCompleted: Boolean = false,
    var isPrioritized: Boolean = false,
    var quantity: Int = 1,
    var price: Double = 0.0,
    var product: ProductDto? = null,
): IMongoDbBaseDto