package dev.haroldjose.familysharedlist.android.pages.familyList

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.Job

interface IFamilyListViewModel {
    var familyListModels: List<FamilyListModel>
    var loading: Boolean
    var newItemName: String
    var quantity: Int
    var filterByCompleted: Boolean

    suspend fun loadData()
    suspend fun add()
    fun showError(e: Throwable)
    suspend fun update(item: FamilyListModel)
    suspend fun remove(item: FamilyListModel)
    suspend fun filterBy(completed: Boolean)
}