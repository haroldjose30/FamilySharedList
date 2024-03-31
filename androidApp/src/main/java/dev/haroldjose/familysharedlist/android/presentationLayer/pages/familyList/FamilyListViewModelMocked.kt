package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

class FamilyListViewModelMocked() : IFamilyListViewModel {
    override var familyListModels: List<FamilyListModel> = arrayListOf(
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
    override suspend fun loadData(tabIndex: FamilyListPageTabEnum) {}
    override suspend fun add() {}
    override suspend fun addBy(barcode: String) {}

    override fun showError(e: Throwable) {}
    override suspend fun update(item: FamilyListModel) {}
    override suspend fun remove(item: FamilyListModel) {}
}