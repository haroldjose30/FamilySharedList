package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.defaultLocalDateTime
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.product.GetProductByCodeUseCase
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseCrashlytics
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class FamilyListViewModel(
    private val getAllFamilyListUseCase: GetAllFamilyListUseCase,
    private val createFamilyListUseCase: CreateFamilyListUseCase,
    private val updateFamilyListUseCase: UpdateFamilyListUseCase,
    private val deleteFamilyListUseCase: DeleteFamilyListUseCase,
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
    private val getProductByCodeUseCase: GetProductByCodeUseCase,
    private val crashlytics: IFirebaseCrashlytics,
): ViewModel(), IFamilyListViewModel {

    override var viewState: FamilyListViewState by mutableStateOf(FamilyListViewState.Initial)
    override var familyListModelsGrouped: Map<LocalDate, List<FamilyListModel>> by mutableStateOf(mapOf())
    override var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var newItemName: String by mutableStateOf("")
    override var selectedItemUuid: String = ""
    override var quantity: Int by mutableStateOf(1)
    override var tabIndex: FamilyListTabType by mutableStateOf(FamilyListTabType.PENDING)
    override var openImageSelectedItem: FamilyListModel? by mutableStateOf(null)

    private lateinit var accountModel: AccountModel
    private var familyListModelsFull: List<FamilyListModel> by mutableStateOf(arrayListOf())

    override var goToSetting: () -> Unit = {}
    override var goToQuickInsert: () -> Unit = {}

    override var sumOfPrioritized: Double by mutableStateOf(0.0)
    override var sumOfPending: Double by mutableStateOf(0.0)
    override var sumOfCompleted: Double by mutableStateOf(0.0)

    override suspend fun loadData(fromNetwork: Boolean) {
        this.tabIndex = tabIndex
        viewState = FamilyListViewState.Loading

        try {
            val accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()
            this.accountModel = accountModel
        } catch (e: Throwable) {
            showError(e)
            return
        }

        if (fromNetwork) {
            try {
                val familyList = getAllFamilyListUseCase.execute()
                familyListModelsFull = familyList.sortedBy { it.name.lowercase() }

            } catch (e: Throwable) {
                showError(e)
                return
            }
        }

        familyListModels = when (tabIndex) {
            FamilyListTabType.PRIORIZED -> familyListModelsFull.filter { it.isPrioritized }
            FamilyListTabType.PENDING -> familyListModelsFull.filter { !it.isCompleted && !it.isPrioritized}
            FamilyListTabType.COMPLETED -> familyListModelsFull.filter { it.isCompleted }
        }

        familyListModelsGrouped = familyListModelsFull
            .filter { it.isCompleted }
            .sortedByDescending { it.isCompletedDate?.date ?: defaultLocalDateTime.date}
            .groupBy { it.isCompletedDate?.date ?: defaultLocalDateTime.date }

        sumOfPrioritized = familyListModelsFull.filter { it.isPrioritized }.sumOf { it.price * it.quantity }
        sumOfPending = familyListModelsFull.filter { !it.isCompleted && !it.isPrioritized }.sumOf { it.price * it.quantity }
        sumOfCompleted = familyListModelsGrouped.entries.sortedByDescending { it.key }.firstOrNull()?.value?.sumOf { it.price * it.quantity} ?: 0.0

        viewState = FamilyListViewState.Initial
    }

    override suspend fun add() {

        if (newItemName.isEmpty())
            return

        if (newItemName.isDigitsOnly()) {
            val barcode = newItemName
            newItemName = ""
            addBy(barcode = barcode)
            return
        }

        val item = FamilyListModel(
            name = newItemName,
            quantity = quantity,
            isPrioritized = this.tabIndex.isPrioritized(),
            isCompleted = false
        )
        newItemName = ""
        viewState = FamilyListViewState.Loading
        try {
            createFamilyListUseCase.execute(item = item)
            loadData(fromNetwork = true)
        } catch (e: Throwable) {
            showError(e)
            return
        }
    }

    override suspend fun addBy(barcode: String) {
        Logger.d("FamilyListSharedViewModel", "barcode: $barcode")
        if (barcode.isEmpty())
            return

        viewState = FamilyListViewState.Loading
        try {
            getProductByCodeUseCase.execute(code = barcode)?.let { productModel ->
                if (productModel.productName.isEmpty()) {
                    viewState = FamilyListViewState.Initial
                    return
                }

                familyListModelsFull.firstOrNull {
                    it.uuid == selectedItemUuid ||
                            it.product?.code == productModel.code ||
                            it.name.compareTo(
                                productModel.productName,
                                ignoreCase = true
                            ) == 0
                }?.let { itemFounded ->
                    // Check if the item is completed and to pending state
                    if (itemFounded.isCompleted) {
                        itemFounded.isCompleted = false
                    }

                    itemFounded.isPrioritized = this.tabIndex.isPrioritized()
                    if (itemFounded.product == null) {
                        itemFounded.name = productModel.productName
                    }
                    itemFounded.product = productModel
                    update(item = itemFounded)
                    if (this.tabIndex.isCompleted())
                        this.tabIndex = FamilyListTabType.PENDING
                    loadData(fromNetwork = true)
                    return
                }

                //if the item not found, create a new one
                val item = FamilyListModel(
                    name = productModel.productName,
                    quantity = quantity,
                    isPrioritized = this.tabIndex.isPrioritized(),
                    isCompleted = false,
                    product = productModel
                )

                createFamilyListUseCase.execute(item = item)
                loadData(fromNetwork = true)
            }

        } catch (e: Throwable) {
            showError(e)
            return
        }
        viewState = FamilyListViewState.Initial
    }

    override fun showError(e: Throwable) {
        e.message?.let { Logger.d("showError", it) }
        crashlytics.record(e)
        viewState = FamilyListViewState.Error(
            message = e.message ?: "Erro desconhecido",
            retryAction = { viewModelScope.launch { loadData(fromNetwork = true) } }
        )
    }

    override suspend fun remove(uuid: String){
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            viewState = FamilyListViewState.Loading
            try {
                deleteFamilyListUseCase.execute(uuid = item.uuid)
                familyListModelsFull = familyListModelsFull.filter { it.uuid != uuid }
                loadData(fromNetwork = false)
                viewState = FamilyListViewState.Initial
            } catch (e: Throwable) {
                showError(e)
                return
            }
        }
    }

    override suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.isCompleted = isCompleted
            if (item.isCompleted) {
                item.isPrioritized = false
                item.isCompletedDate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            }
            update(item)
        }
    }

    override suspend fun updateIsPrioritized(uuid: String, isPrioritized: Boolean) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.isPrioritized = isPrioritized
            update(item)
        }
    }

    override suspend fun updateName(uuid: String, name: String) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.name = name
            update(item)
        }
    }

    override suspend fun updateQuantity(uuid: String, quantity: Int) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.quantity = quantity
            update(item)
        }
    }

    override suspend fun updatePrice(uuid: String, price: Double) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.price = price
            update(item)
        }
    }

    override fun openImage(item: FamilyListModel) {
        openImageSelectedItem = item
    }

    private suspend fun update(item: FamilyListModel){
        viewState = FamilyListViewState.Loading
        try {
            updateFamilyListUseCase.execute(item = item)
            loadData(fromNetwork = false)
        } catch (e: Throwable) {
            showError(e)
            return
        }
        viewState = FamilyListViewState.Initial
    }
}
