package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorViewState

class NavigatorViewModelMock(): ViewModel(), INavigatorViewModel {
    override var viewState: NavigatorViewState = NavigatorViewState.none
    override suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController) {}
}