package dev.haroldjose.familysharedlist.domainLayer.models

import dev.haroldjose.familysharedlist.getPlatform


data class FamilyListModel(
    var uuid: String = "",
    var name: String,
    var isCompleted: Boolean = false,
    var isPrioritized: Boolean = false,
    var quantity: Int = 1,
    var price: Double = 0.0,
    var product: ProductModel? = null,
) {
    init {
        this.uuid = uuid.ifEmpty { getPlatform().generateUUID() }
    }
}