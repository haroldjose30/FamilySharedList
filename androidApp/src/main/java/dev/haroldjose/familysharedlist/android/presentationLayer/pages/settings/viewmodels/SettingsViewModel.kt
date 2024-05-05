package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.android.app.MainApplication
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetAccountUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetLocalAccountUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.SetSharedAccountByCodeUseCase
import dev.haroldjose.familysharedlist.getPlatform
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseCrashlytics
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
    private val setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase,
    private val crashlytics: IFirebaseCrashlytics,
): ViewModel(), ISettingsViewModel {

    private val constAccountsSharedWithMeTitle = "Acessar conta compartilhada"
    private val constAccountsSharedWithMeSubtitle = "Obs: atualmente limitada apenas a 1 conta"
    private val constLoadingMessage = "carregando..."

    override var viewState: SettingsViewState by mutableStateOf(
        SettingsViewState.Initial(
            accountShortCodeForShareTitle = constLoadingMessage,
            accountsSharedWithMeTitle = constAccountsSharedWithMeTitle,
            accountsSharedWithMeSubtitle = constAccountsSharedWithMeSubtitle
        )
    )
    override var myAccount: AccountModel? = null

    override var goBack: () -> Unit = {}

    override suspend fun getAccount() {
        viewState = SettingsViewState.Loading
        try {
            val accountUuid = getLocalAccountUuidUseCase.execute()
            myAccount = getAccountUseCase.execute(accountUuid = accountUuid)

            var accountShortCodeForShareTitle = myAccount?.accountShortCodeForShare ?: constLoadingMessage
            var accountsSharedWithMeTitle = constAccountsSharedWithMeTitle
            var accountsSharedWithMeSubtitle = constAccountsSharedWithMeSubtitle
            myAccount?.accountsSharedWithMe?.let {
                if (it.isNotEmpty()) {
                    accountsSharedWithMeTitle = "Acessando a conta:"
                    accountsSharedWithMeSubtitle = it.first()
                }
            }
            viewState = SettingsViewState.Success(
                accountShortCodeForShareTitle = accountShortCodeForShareTitle,
                accountsSharedWithMeTitle = accountsSharedWithMeTitle,
                accountsSharedWithMeSubtitle = accountsSharedWithMeSubtitle
            )
        } catch (e: Throwable) {
            showError(e)
            return
        }
    }

    private fun showError(e: Throwable) {
        e.message?.let { Logger.d("showError", it) }
        crashlytics.record(e)
        viewState = SettingsViewState.Error(
            message = "Erro ao acessar a conta",
            retryAction = {
                this.viewModelScope.launch { getAccount() }
            }
        )
    }

    override suspend fun accessSharedAccountWithCode(code: String) {
        viewState = SettingsViewState.Loading
        try {
            val accountUuid = getLocalAccountUuidUseCase.execute()
            setSharedAccountByCodeUseCase
                .execute(
                    accountUuid = accountUuid,
                    code = code
                )
            getAccount()
        } catch (e: Throwable) {
            showError(e)
            return
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
                val longVersionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    pInfo.longVersionCode
                } else {
                    "0p"
                }
                return "Alpha $versionName (b$longVersionCode)"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return ""
        }
    }
}