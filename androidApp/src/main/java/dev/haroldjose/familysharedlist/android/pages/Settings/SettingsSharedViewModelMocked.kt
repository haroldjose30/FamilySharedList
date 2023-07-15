package dev.haroldjose.familysharedlist.android.pages.Settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.ISettingsSharedViewModel

class SettingsSharedViewModelMocked: ISettingsSharedViewModel {

    override var myAccount: AccountModel? by mutableStateOf(null)

    override suspend fun getAccount() {
        myAccount = AccountModel(uuid = "mockedUUid")
    }

    override suspend fun accessSharedAccountWithCode(code: String) {

    }
}