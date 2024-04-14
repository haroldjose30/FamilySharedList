package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.lifecycle.ViewModel

class NavigatorViewModelMock(): ViewModel(), INavigatorViewModel {
    override suspend fun checkIfNeedToCreateNewAccount() {}
}