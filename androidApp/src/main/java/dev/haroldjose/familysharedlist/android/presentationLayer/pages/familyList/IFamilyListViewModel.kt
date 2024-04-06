package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

interface IFamilyListViewModel {
    var familyListModelsFiltered: List<FamilyListModel>
    var loading: Boolean
    var newItemName: String
    var quantity: Int
    var tabIndex: FamilyListPageTabEnum

    var goToSetting: () -> Unit
    var goToQuickInsert: () -> Unit

    suspend fun loadData(tabIndex: FamilyListPageTabEnum, fromNetwork: Boolean)
    suspend fun add()
    suspend fun addBy(barcode: String)
    fun showError(e: Throwable)
    suspend fun remove(uuid: String)
    suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean)
    suspend fun updateIsPrioritized(uuid: String, isPrioritized: Boolean)
    suspend fun updateName(uuid: String, name: String)
    suspend fun updateQuantity(uuid: String, quantity: Int)
}