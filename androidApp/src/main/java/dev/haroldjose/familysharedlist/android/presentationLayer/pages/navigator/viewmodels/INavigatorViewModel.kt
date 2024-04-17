package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.navigation.NavHostController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorViewState

interface INavigatorViewModel {

    var viewState: NavigatorViewState
    suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController)
}