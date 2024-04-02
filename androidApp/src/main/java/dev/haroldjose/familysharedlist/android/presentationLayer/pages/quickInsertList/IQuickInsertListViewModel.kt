package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList

interface IQuickInsertListViewModel {

    var loading:Boolean
    var text:String
    var goToFamilyListPage: () -> Unit
    suspend fun quickInsertItem()
}