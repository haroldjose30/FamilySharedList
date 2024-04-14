package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.datetime.LocalDate

interface IFamilyListViewModel {
    val familyListModelsGrouped: Map<LocalDate, List<FamilyListModel>>
    var familyListModels: List<FamilyListModel>
    var loading: Boolean
    var newItemName: String
    var selectedItemUuid: String
    var quantity: Int
    var tabIndex: FamilyListPageTabEnum

    var goToSetting: () -> Unit
    var goToQuickInsert: () -> Unit

    var sumOfPrioritized: Double
    var sumOfPending: Double
    var sumOfCompleted: Double

    suspend fun loadData(fromNetwork: Boolean)
    suspend fun add()
    suspend fun addBy(barcode: String)
    fun showError(e: Throwable)
    suspend fun remove(uuid: String)
    suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean)
    suspend fun updateIsPrioritized(uuid: String, isPrioritized: Boolean)
    suspend fun updateName(uuid: String, name: String)
    suspend fun updateQuantity(uuid: String, quantity: Int)
    suspend fun updatePrice(uuid: String, price: Double)
}