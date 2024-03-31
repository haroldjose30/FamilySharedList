package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

enum class FamilyListPageTabEnum(val value: Int) {
    PRIORIZED(0) {
        override fun isPriorized() = this.value == 0
        override fun isPending() = this.value == 0
        override fun isCompleted() = this.value == 0
     },
    PENDING(1){
        override fun isPriorized() = this.value == 1
        override fun isPending() = this.value == 1
        override fun isCompleted() = this.value == 1
    },
    COMPLETED(2){
        override fun isPriorized() = this.value == 2
        override fun isPending() = this.value == 2
        override fun isCompleted() = this.value == 2
    };

    abstract fun isPriorized(): Boolean
    abstract fun isPending(): Boolean
    abstract fun isCompleted(): Boolean

    companion object {
        fun getBy(value: Int): FamilyListPageTabEnum = entries.firstOrNull { it.value == value } ?: PENDING
    }
}

interface IFamilyListViewModel {
    var familyListModels: List<FamilyListModel>
    var loading: Boolean
    var newItemName: String
    var quantity: Int
    var tabIndex: FamilyListPageTabEnum

    suspend fun loadData(tabIndex: FamilyListPageTabEnum)
    suspend fun add()
    suspend fun addBy(barcode: String)
    fun showError(e: Throwable)
    suspend fun update(item: FamilyListModel)
    suspend fun remove(item: FamilyListModel)
}