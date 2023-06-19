package dev.haroldjose.familysharedlist.domainLayer.models

import dev.haroldjose.familysharedlist.generateUUID

data class FamilyListModel(
    var uuid: String = "",
    var name: String,
    var isCompleted: Boolean = false,
    var quantity: Int = 1
) {
    init {
        this.uuid = if (uuid.isEmpty()) generateUUID() else uuid
        this.name = name
        this.isCompleted = isCompleted
        this.quantity = quantity
    }
}