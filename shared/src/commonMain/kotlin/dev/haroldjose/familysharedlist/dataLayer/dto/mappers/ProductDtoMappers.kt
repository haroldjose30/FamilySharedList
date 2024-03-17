package dev.haroldjose.familysharedlist.dataLayer.dto.mappers

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.openfoodfacts.response.OpenFoodFactsGetProductByGetResponse
import dev.haroldjose.familysharedlist.dataLayer.dto.ProductDto


internal fun OpenFoodFactsGetProductByGetResponse.toDto(): ProductDto {
    return ProductDto(
        code = this.product?.code ?: "",
        imageFrontSmallUrl = this.product?.imageFrontSmallUrl,
        imageFrontUrl = this.product?.imageFrontUrl,
        imageUrl = this.product?.imageUrl,
        productName =  this.product?.productName ?: ""
    )
}