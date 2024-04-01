package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

enum class FamilyListPageTabEnum(val value: Int) {
    PRIORIZED(0) {
        override fun isPriorized() = true
        override fun isPending() = false
        override fun isCompleted() = false
     },
    PENDING(1){
        override fun isPriorized() = false
        override fun isPending() = true
        override fun isCompleted() = false
    },
    COMPLETED(2){
        override fun isPriorized() = false
        override fun isPending() = false
        override fun isCompleted() = true
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

    var goToSetting: () -> Unit
    var goToEditItem: (FamilyListModel) -> Unit

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