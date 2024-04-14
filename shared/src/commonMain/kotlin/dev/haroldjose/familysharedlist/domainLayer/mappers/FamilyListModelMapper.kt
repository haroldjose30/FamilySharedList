package dev.haroldjose.familysharedlist.domainLayer.mappers

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

internal fun FamilyListDto.toModel(): FamilyListModel {
    return FamilyListModel(
        uuid = this.uuid,
        name = this.name,
        isCompleted = this.isCompleted,
        isCompletedDate = this.isCompletedDate,
        isPrioritized = this.isPrioritized,
        quantity = this.quantity,
        price = this.price,
        product = this.product?.toModel()
    )
}

internal fun FamilyListModel.toDto(): FamilyListDto {
    return FamilyListDto(
        uuid = this.uuid,
        name = this.name,
        isCompleted = this.isCompleted,
        isCompletedDate = this.isCompletedDate,
        isPrioritized = this.isPrioritized,
        quantity = this.quantity,
        price = this.price,
        product = this.product?.toDto()
    )
}