package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls

sealed class QuickInsertListViewState {

    object Initial: QuickInsertListViewState()
    object Loading: QuickInsertListViewState()
    object Success: QuickInsertListViewState()
    data class Error(
        val message: String,
        val retryAction: (() -> Unit)? = null
    ) : QuickInsertListViewState()
}