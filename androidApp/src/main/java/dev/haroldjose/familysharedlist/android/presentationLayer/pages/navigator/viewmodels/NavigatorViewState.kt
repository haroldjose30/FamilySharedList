package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

sealed class NavigatorViewState {

    object Initial: NavigatorViewState()
    object Loading: NavigatorViewState()
    object Success: NavigatorViewState()
    data class Error(val message: String) : NavigatorViewState()
}