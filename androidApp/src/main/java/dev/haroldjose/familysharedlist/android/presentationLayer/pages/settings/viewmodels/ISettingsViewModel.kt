package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

interface ISettingsViewModel {

    var myAccount: AccountModel?
    var accountShortCodeForShareTitle: String
    var accountsSharedWithMeTitle: String
    var accountsSharedWithMeSubtitle: String

    var goBack: () -> Unit

    suspend fun getAccount()
    suspend fun accessSharedAccountWithCode(code: String)
    suspend fun shareMyCode()
    suspend fun openAppHomePage()

    fun getVersion(): String

}