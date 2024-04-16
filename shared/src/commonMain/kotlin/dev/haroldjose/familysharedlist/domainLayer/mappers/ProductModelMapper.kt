package dev.haroldjose.familysharedlist.domainLayer.mappers

import dev.haroldjose.familysharedlist.dataLayer.dto.ProductDto
import dev.haroldjose.familysharedlist.domainLayer.models.ProductModel

internal fun ProductDto.toModel(): ProductModel {
    return ProductModel(
        code = this.code,
        imageFrontSmallUrl = this.imageFrontSmallUrl,
        imageFrontUrl = this.imageFrontUrl,
        imageUrl = this.imageUrl,
        productName =  this.productName
    )
}

internal fun ProductModel.toDto(): ProductDto {
    return ProductDto(
        code = this.code,
        imageFrontSmallUrl = this.imageFrontSmallUrl,
        imageFrontUrl = this.imageFrontUrl,
        imageUrl = this.imageUrl,
        productName =  this.productName
    )
}