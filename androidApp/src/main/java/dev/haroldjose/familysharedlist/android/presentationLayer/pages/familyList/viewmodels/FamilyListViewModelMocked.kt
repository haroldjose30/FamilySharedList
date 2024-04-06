package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class FamilyListViewModelMocked : IFamilyListViewModel {
    override var familyListModelsFiltered: List<FamilyListModel> = arrayListOf(
        FamilyListModel(
            uuid = "sample1",
            name = "Mock Item 01",
            quantity = 1,
            isCompleted = false
        ),
        FamilyListModel(
            uuid = "sample2",
            name = "Mock Item 02",
            quantity = 2,
            isCompleted = true
        ),
        FamilyListModel(
            uuid = "sample3",
            name = "Mock Item 03",
            quantity = 3,
            isCompleted = false
        ),
    )
    override var loading: Boolean = false
    override var newItemName: String = "newItem"
    override var quantity: Int = 1
    override var tabIndex: FamilyListPageTabEnum = FamilyListPageTabEnum.PRIORIZED

    override var goToSetting: () -> Unit = {}
    override var goToQuickInsert: () -> Unit = {}

    override suspend fun loadData(tabIndex: FamilyListPageTabEnum, fromNetwork: Boolean) {}
    override suspend fun add() {}
    override suspend fun addBy(barcode: String) {}

    override fun showError(e: Throwable) {}
    override suspend fun remove(uuid: String) {}

    override suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean) {}

    override suspend fun updateIsPrioritized(uuid: String, isPrioritized: Boolean) {}

    override suspend fun updateName(uuid: String, name: String) {}

    override suspend fun updateQuantity(uuid: String, quantity: Int) {}
}