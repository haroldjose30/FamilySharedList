package dev.haroldjose.familysharedlist.domainLayer.models

import dev.haroldjose.familysharedlist.generateUUID
import dev.haroldjose.familysharedlist.getPlatform
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
    var platform: String = getPlatform().name
) {
    init {
        this.uuid = if (uuid.isEmpty()) generateUUID() else uuid
        this.name = name
        this.createdDate = createdDate
        this.updatedDate = updatedDate
        this.name = name
        this.email = email
        this.myAccountIsSharedWith = myAccountIsSharedWith
        this.accountsSharedWithMe = accountsSharedWithMe
        this.platform = getPlatform().name
    }
}