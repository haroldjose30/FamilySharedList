package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.Router
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseCrashlytics

class NavigatorViewModel(
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
    private val crashlytics: IFirebaseCrashlytics,
): ViewModel(), INavigatorViewModel {
    override var viewState: NavigatorViewState by mutableStateOf(NavigatorViewState.Initial)

    override suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController, initialRoute: Router) {
        viewState = NavigatorViewState.Loading
        try {
            getOrCreateAccountFromLocalUuidUseCase.execute()
            viewState = NavigatorViewState.Success
            navController.navigate(initialRoute.value)
        } catch (e: Throwable) {
            crashlytics.record(e)
            viewState = NavigatorViewState.Error(e.message ?: "Ocorreu um erro inesperado. Tente novamente.")
            return
        }
    }
}