package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

sealed class FamilyListViewState {

    object Initial: FamilyListViewState()
    object Loading: FamilyListViewState()
    object Success: FamilyListViewState()
    data class Error(
        val message: String,
        val retryAction: (() -> Unit)? = null
    ) : FamilyListViewState()
}