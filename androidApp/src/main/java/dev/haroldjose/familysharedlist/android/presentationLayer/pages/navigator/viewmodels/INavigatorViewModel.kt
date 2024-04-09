package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

interface INavigatorViewModel {
    suspend fun checkIfNeedToCreateNewAccount()
}