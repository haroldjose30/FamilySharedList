package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator

interface INavigatorViewModel {
    suspend fun checkIfNeedToCreateNewAccount()
}