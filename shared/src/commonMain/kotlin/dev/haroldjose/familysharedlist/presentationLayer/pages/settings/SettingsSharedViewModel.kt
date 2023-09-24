package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.openShareOptionsWithText
import dev.haroldjose.familysharedlist.openUrlOnDefaultBrowser

class SettingsSharedViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
    private val setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase
): ISettingsSharedViewModel {

    private val constAccountsSharedWithMeTitle = "Acessar conta compartilhada"
    private val constAccountsSharedWithMeSubtitle = "Obs: atualmente limitada apenas a 1 conta"
    private val constLoadingMessage = "carregando..."

    override var myAccount: AccountModel? by mutableStateOf(null)
    override var accountShortCodeForShareTitle: String by mutableStateOf(constLoadingMessage)
    override var accountsSharedWithMeTitle: String by mutableStateOf(constAccountsSharedWithMeTitle)
    override var accountsSharedWithMeSubtitle: String by mutableStateOf(constAccountsSharedWithMeSubtitle)

    override suspend fun getAccount() {
        val accountUuid = getLocalAccountUuidUseCase.execute()
        myAccount = getAccountUseCase.execute(accountUuid = accountUuid)
        accountShortCodeForShareTitle = myAccount?.accountShortCodeForShare ?: constLoadingMessage

        accountsSharedWithMeTitle = constAccountsSharedWithMeTitle
        accountsSharedWithMeSubtitle = constAccountsSharedWithMeSubtitle
        myAccount?.accountsSharedWithMe?.let {
            if (it.isNotEmpty()) {
                accountsSharedWithMeTitle = "Acessando a conta:"
                accountsSharedWithMeSubtitle = it.first()
            }
        }
    }

    override suspend fun accessSharedAccountWithCode(code: String) {
        //TODO: implement loading
        val accountUuid = getLocalAccountUuidUseCase.execute()
        val result = setSharedAccountByCodeUseCase.execute(
            accountUuid = accountUuid,
            code = code
        )
        if (result) {
            getAccount()
        }
    }

    override suspend fun shareMyCode() {
        myAccount?.accountShortCodeForShare?.let {
            openShareOptionsWithText(text = it)
        }
    }

    override suspend fun openAppHomePage() {
        openUrlOnDefaultBrowser(url = "https://github.com/haroldjose30/FamilySharedList")
    }
}