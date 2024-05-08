package dev.haroldjose.familysharedlist.domainLayer.models

import dev.haroldjose.familysharedlist.getPlatform
import generateShortCodeByUuid
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class AccountModel(
    var uuid: String,
    var createdDate: Instant = Clock.System.now(),
    var updatedDate: Instant = Clock.System.now(),
    var name: String = "",
    var email: String = "",
    var myAccountIsSharedWith: List<String> = arrayListOf(),
    var accountsSharedWithMe: List<String> = arrayListOf(),
    var platform: String = getPlatform().name,
    var accountShortCodeForShare: String = ""
) {
    init {
        this.uuid = uuid.ifEmpty { getPlatform().generateUUID() }
        this.platform = getPlatform().name
        this.accountShortCodeForShare = accountShortCodeForShare.ifEmpty { generateShortCodeByUuid(uuid = this.uuid) }
    }

    constructor(uuid: String): this(
        uuid = uuid,
        createdDate = Clock.System.now(),
        updatedDate = Clock.System.now(),
        name = "",
        email = "",
        myAccountIsSharedWith = arrayListOf(),
        accountsSharedWithMe = arrayListOf(),
        platform = getPlatform().name,
        accountShortCodeForShare = ""
    )
}


