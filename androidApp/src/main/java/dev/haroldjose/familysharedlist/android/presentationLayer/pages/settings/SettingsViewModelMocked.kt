package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class SettingsViewModelMocked(): ISettingsViewModel {

    override var myAccount: AccountModel? by mutableStateOf(null)
    override var accountShortCodeForShareTitle: String by mutableStateOf("")
    override var accountsSharedWithMeTitle: String by mutableStateOf("")
    override var accountsSharedWithMeSubtitle: String by mutableStateOf("")

    override suspend fun getAccount() {
        myAccount = AccountModel(uuid = "mockedUUid")
        accountShortCodeForShareTitle = "XY5A4"
        accountsSharedWithMeTitle = "Mocked Title"
        accountsSharedWithMeSubtitle = "DbAcc_74287346238746828i3"
    }
    override suspend fun accessSharedAccountWithCode(code: String) {}
    override suspend fun shareMyCode() {}
    override suspend fun openAppHomePage() {}
}