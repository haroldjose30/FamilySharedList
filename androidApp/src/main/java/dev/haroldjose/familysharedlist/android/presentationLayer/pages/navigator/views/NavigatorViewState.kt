package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views

sealed class NavigatorViewState {

    object none: NavigatorViewState()
    object Loading: NavigatorViewState()
    object Success: NavigatorViewState()
    data class Error(val message: String) : NavigatorViewState()
}