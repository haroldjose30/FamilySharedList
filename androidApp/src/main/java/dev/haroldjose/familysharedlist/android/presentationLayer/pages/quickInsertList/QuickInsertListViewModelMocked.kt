package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList

class QuickInsertListViewModelMocked: IQuickInsertListViewModel {
    override var loading: Boolean = false
    override var text: String = ""
    override var goToFamilyListPage: () -> Unit = {}
    override suspend fun quickInsertItem() {}
}