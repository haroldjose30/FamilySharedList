package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.Router

class NavigatorViewModelMock(): ViewModel(), INavigatorViewModel {
    override var viewState: NavigatorViewState = NavigatorViewState.Initial
    override suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController,initialRoute: Router) {}
}