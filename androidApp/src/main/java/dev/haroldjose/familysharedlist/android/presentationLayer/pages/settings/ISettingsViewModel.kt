package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings

import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

interface ISettingsViewModel {

    var myAccount: AccountModel?
    var accountShortCodeForShareTitle: String
    var accountsSharedWithMeTitle: String
    var accountsSharedWithMeSubtitle: String

    suspend fun getAccount()
    suspend fun accessSharedAccountWithCode(code: String)
    suspend fun shareMyCode()
    suspend fun openAppHomePage()

}