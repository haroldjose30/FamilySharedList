package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase

class SettingsSharedViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase
): ISettingsSharedViewModel {

    override var myAccount: AccountModel? by mutableStateOf(null)

    override suspend fun getAccount() {
        val accountUuid = getLocalAccountUuidUseCase.execute()
        myAccount = getAccountUseCase.execute(accountUuid = accountUuid)
    }

    override suspend fun accessSharedAccountWithCode(code: String) {

    }

}