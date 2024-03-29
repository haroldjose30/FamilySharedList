package dev.haroldjose.familysharedlist.domainLayer.mappers

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

internal fun FamilyListDto.toModel(): FamilyListModel {
    return FamilyListModel(
        uuid = this.uuid,
        name = this.name,
        isCompleted = this.isCompleted,
        isPriorized = this.isPriorized,
        quantity = this.quantity
    )
}

internal fun FamilyListModel.toDto(): FamilyListDto {
    return FamilyListDto(
        uuid = this.uuid,
        name = this.name,
        isCompleted = this.isCompleted,
        isPriorized = this.isPriorized,
        quantity = this.quantity
    )
}