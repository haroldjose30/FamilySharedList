package dev.haroldjose.familysharedlist.domainLayer.models

import dev.haroldjose.familysharedlist.getPlatform
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class FamilyListModel(
    var uuid: String = "",
    var name: String,
    var isCompleted: Boolean = false,
    var isCompletedDate: LocalDateTime? = null,
    var isPrioritized: Boolean = false,
    var quantity: Int = 1,
    var price: Double = 0.0,
    var product: ProductModel? = null,
) {
    init {
        this.uuid = uuid.ifEmpty { getPlatform().generateUUID() }
    }
}