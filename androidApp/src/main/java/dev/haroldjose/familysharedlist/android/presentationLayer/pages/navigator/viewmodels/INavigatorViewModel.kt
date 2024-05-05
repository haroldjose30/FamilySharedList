package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.navigation.NavHostController

interface INavigatorViewModel {

    var viewState: NavigatorViewState
    suspend fun checkIfNeedToCreateNewAccount(navController: NavHostController)
}