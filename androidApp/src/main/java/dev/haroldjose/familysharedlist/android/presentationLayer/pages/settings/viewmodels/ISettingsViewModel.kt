package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

interface ISettingsViewModel {
    var viewState: SettingsViewState
    var myAccount: AccountModel?

    var goBack: () -> Unit

    suspend fun getAccount()
    suspend fun accessSharedAccountWithCode(code: String)
    suspend fun shareMyCode()
    suspend fun openAppHomePage()

    fun getVersion(): String

}