package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class NavigatorViewModelMock(): ViewModel(), INavigatorViewModel {
    override var viewState: NavigatorViewState = NavigatorViewState.Initial
    override suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController) {}
}