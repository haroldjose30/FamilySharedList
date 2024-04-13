package dev.haroldjose.familysharedlist.dataLayer.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto (
    val code: String,
    val imageFrontSmallUrl: String?,
    val imageFrontUrl: String?,
    val imageUrl: String?,
    val productName: String
)