package dev.haroldjose.familysharedlist.dataLayer.dto

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.IMongoDbBaseDto
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    override val uuid: String = "",
    val createdDate: Instant = Clock.System.now(),
    val updatedDate: Instant = Clock.System.now(),
    val name: String = "",
    val email: String = "",
    val myAccountIsSharedWith: List<String> = arrayListOf(),
    var accountsSharedWithMe: List<String> = arrayListOf(),
    val platform: String = "",
    val accountShortCodeForShare: String = ""
) : IMongoDbBaseDto