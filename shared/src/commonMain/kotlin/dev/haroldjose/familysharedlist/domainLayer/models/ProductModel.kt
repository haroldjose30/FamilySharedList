package dev.haroldjose.familysharedlist.domainLayer.models

data class ProductModel (
    val code: String,
    val imageFrontSmallUrl: String?,
    val imageFrontUrl: String?,
    val imageUrl: String?,
    val productName: String
)