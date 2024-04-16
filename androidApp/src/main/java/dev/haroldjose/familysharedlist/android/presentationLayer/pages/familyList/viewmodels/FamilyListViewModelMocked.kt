package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.defaultLocalDateTime
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.datetime.LocalDate

class FamilyListViewModelMocked: ViewModel(), IFamilyListViewModel {
    override val familyListModelsGrouped: Map<LocalDate, List<FamilyListModel>> by mutableStateOf(
        Samples.FamilyList.list1.groupBy { it.isCompletedDate?.date ?: defaultLocalDateTime.date }
    )
    override var familyListModels: List<FamilyListModel> = Samples.FamilyList.list1
    override var loading: Boolean = false
    override var newItemName: String = "newItem"
    override var selectedItemUuid: String = ""
    override var quantity: Int = 1
    override var tabIndex: FamilyListPageTabEnum = FamilyListPageTabEnum.PRIORIZED
    override var openImageSelectedItem: FamilyListModel? = Samples.FamilyList.nutella

    override var goToSetting: () -> Unit = {}
    override var goToQuickInsert: () -> Unit = {}

    override var sumOfPrioritized: Double = 0.0
    override var sumOfPending: Double = 1.23
    override var sumOfCompleted: Double = 44.92

    override suspend fun loadData(fromNetwork: Boolean) {}
    override suspend fun add() {}
    override suspend fun addBy(barcode: String) {}

    override fun showError(e: Throwable) {}
    override suspend fun remove(uuid: String) {}

    override suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean) {}

    override suspend fun updateIsPrioritized(uuid: String, isPrioritized: Boolean) {}

    override suspend fun updateName(uuid: String, name: String) {}

    override suspend fun updateQuantity(uuid: String, quantity: Int) {}

    override suspend fun updatePrice(uuid: String, price: Double) {}

    override fun openImage(item: FamilyListModel) {}
}