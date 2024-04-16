package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.haroldjose.familysharedlist.android.app.MainApplication
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.getPlatform


class SettingsViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
    private val setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase
): ViewModel(), ISettingsViewModel {

    private val constAccountsSharedWithMeTitle = "Acessar conta compartilhada"
    private val constAccountsSharedWithMeSubtitle = "Obs: atualmente limitada apenas a 1 conta"
    private val constLoadingMessage = "carregando..."

    override var myAccount: AccountModel? by mutableStateOf(null)
    override var accountShortCodeForShareTitle: String by mutableStateOf(constLoadingMessage)
    override var accountsSharedWithMeTitle: String by mutableStateOf(constAccountsSharedWithMeTitle)
    override var accountsSharedWithMeSubtitle: String by mutableStateOf(constAccountsSharedWithMeSubtitle)
    override var goBack: () -> Unit = {}

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
            getPlatform().openShareOptionsWithText(text = it)
        }
    }

    override suspend fun openAppHomePage() {
        getPlatform().openUrlOnDefaultBrowser(url = "https://github.com/haroldjose30/FamilySharedList")
    }


    override fun getVersion(): String {
        try {
            MainApplication.applicationContext().let {
                val pInfo: PackageInfo = it.packageManager. getPackageInfo(it.getPackageName(), 0)
                val versionName = pInfo.versionName
                val longVersionCode = pInfo.longVersionCode
                return "Alpha $versionName (b$longVersionCode)"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return ""
        }
    }
}