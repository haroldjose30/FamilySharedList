package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

interface ISettingsSharedViewModel {

    var myAccount: AccountModel?

    suspend fun getAccount()
    suspend fun accessSharedAccountWithCode(code: String)
}