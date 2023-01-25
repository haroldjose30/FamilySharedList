package dev.haroldjose.sharedfamilylist.domainLayer.extensions

import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel
import dev.haroldjose.sharedfamilylist.dataLayer.dto.FamilyListDto

internal fun FamilyListDto.toModel(): FamilyListModel {
    return FamilyListModel(
        uuid = this.uuid,
        name = this.name,
        isCompleted = this.isCompleted,
        quantity = this.quantity
    )
}