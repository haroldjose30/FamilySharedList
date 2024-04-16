package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls

import androidx.lifecycle.ViewModel

class QuickInsertListViewModelMocked: ViewModel(), IQuickInsertListViewModel {
    override var loading: Boolean = false
    override var text: String = ""
    override var goToFamilyListPage: () -> Unit = {}
    override suspend fun quickInsertItem() {}
}