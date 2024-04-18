package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class SettingsViewModelMocked(): ViewModel(), ISettingsViewModel {
    override var viewState: SettingsViewState by mutableStateOf(
        SettingsViewState.Initial(
            accountShortCodeForShareTitle = "XY5A4",
            accountsSharedWithMeTitle = "Mocked Title",
            accountsSharedWithMeSubtitle = "DbAcc_74287346238746828i3"
        )
    )
    override var myAccount: AccountModel? by mutableStateOf(null)
    override var goBack: () -> Unit = {}

    override suspend fun getAccount() {
        myAccount = AccountModel(uuid = "mockedUUid")
    }
    override suspend fun accessSharedAccountWithCode(code: String) {}
    override suspend fun shareMyCode() {}
    override suspend fun openAppHomePage() {}
    override fun getVersion(): String {
        return "1.0.0 (123456)"
    }
}