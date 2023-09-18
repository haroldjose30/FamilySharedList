package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

interface IFamilyListSharedViewModel {
    var familyListModels: List<FamilyListModel>
    var loading: Boolean
    var newItemName: String
    var quantity: Int
    var tabIndex: Int

    suspend fun loadData(tabIndex: Int)
    suspend fun add()
    fun showError(e: Throwable)
    suspend fun update(item: FamilyListModel)
    suspend fun remove(item: FamilyListModel)
}