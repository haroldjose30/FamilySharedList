package dev.haroldjose.familysharedlist.dataLayer.dto

import kotlinx.serialization.Serializable

@Serializable
data class FamilyListDto(
    val uuid: String = "",
    val name: String = "",
    val isCompleted: Boolean = false,
    var quantity: Int = 1
)