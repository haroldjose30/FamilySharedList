package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls

interface IQuickInsertListViewModel {
    var viewState: QuickInsertListViewState
    var text:String
    var goToFamilyListPage: () -> Unit
    suspend fun quickInsertItem()
}