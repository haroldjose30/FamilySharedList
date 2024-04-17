package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorViewRouter
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorViewState
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase

class NavigatorViewModel(
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
): ViewModel(), INavigatorViewModel {
    override var viewState: NavigatorViewState by mutableStateOf(NavigatorViewState.none)

    override suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController) {
        viewState = NavigatorViewState.Loading
        try {
            getOrCreateAccountFromLocalUuidUseCase.execute()
            viewState = NavigatorViewState.Success
            navController.navigate(NavigatorViewRouter.FAMILY_LIST.value)
        } catch (e: Throwable) {
            viewState = NavigatorViewState.Error(e.message ?: "Ocorreu um erro inesperado. Tente novamente.")
            return
        }
    }
}