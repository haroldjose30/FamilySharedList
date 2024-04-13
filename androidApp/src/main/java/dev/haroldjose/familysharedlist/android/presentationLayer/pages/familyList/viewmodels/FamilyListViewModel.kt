package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.product.GetProductByCodeUseCase
import dev.haroldjose.familysharedlist.getPlatform

class FamilyListViewModel(
    private val getAllFamilyListUseCase: GetAllFamilyListUseCase,
    private val createFamilyListUseCase: CreateFamilyListUseCase,
    private val updateFamilyListUseCase: UpdateFamilyListUseCase,
    private val deleteFamilyListUseCase: DeleteFamilyListUseCase,
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
    private val getProductByCodeUseCase: GetProductByCodeUseCase
): IFamilyListViewModel {
    private var familyListModelsFull: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var familyListModelsFiltered: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var loading:Boolean by mutableStateOf(false)
    override var newItemName: String by mutableStateOf("")
    override var selectedItemUuid: String = ""
    override var quantity: Int by mutableStateOf(1)
    override var tabIndex: FamilyListPageTabEnum by mutableStateOf(FamilyListPageTabEnum.PENDING)
    private lateinit var accountModel: AccountModel

    override var goToSetting: () -> Unit = {}
    override var goToQuickInsert: () -> Unit = {}

    override suspend fun loadData(tabIndex: FamilyListPageTabEnum, fromNetwork: Boolean) {
        this.tabIndex = tabIndex
        loading = true

        accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()

        if (fromNetwork) {
            familyListModelsFull = getAllFamilyListUseCase.execute().sortedBy { it.name.lowercase() }
        }

        familyListModelsFiltered = when (tabIndex) {
            FamilyListPageTabEnum.PRIORIZED -> familyListModelsFull
                .filter { it.isPrioritized }

            FamilyListPageTabEnum.PENDING -> familyListModelsFull
                .filter { !it.isCompleted && !it.isPrioritized}

            FamilyListPageTabEnum.COMPLETED -> familyListModelsFull
                .filter { it.isCompleted }
        }

        loading = false
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
        loading = true
        createFamilyListUseCase.execute(item = item)
        loadData(this.tabIndex, fromNetwork = true)
    }

    override suspend fun addBy(barcode: String) {
        Logger.d("FamilyListSharedViewModel", "barcode: $barcode")
        if (barcode.isEmpty())
            return

        loading = true
        getProductByCodeUseCase.execute(code = barcode)?.let { productModel ->

            if (productModel.productName.isEmpty()) {
                loading = false
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
                    this.tabIndex = FamilyListPageTabEnum.PENDING
                loadData(this.tabIndex, fromNetwork = true)
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
            Logger.d("FamilyListSharedViewModel", "createFamilyListUseCase executed")
            loadData(this.tabIndex, fromNetwork = true)
            Logger.d("FamilyListSharedViewModel", "loadData executed")
        }
        loading = false
    }

    override fun showError(e: Throwable) {
        //TODO: implement log in shared module
        //e.message?.let { Log.d("showError", it) }
    }

    override suspend fun remove(uuid: String){
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            loading = true
            deleteFamilyListUseCase.execute(uuid = item.uuid)
            familyListModelsFull = familyListModelsFull.filter { it.uuid != uuid }
            loadData(this.tabIndex, fromNetwork = false)
            loading = false
        }
    }

    override suspend fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        familyListModelsFull.firstOrNull { it.uuid ==  uuid}?.let { item ->
            item.isCompleted = isCompleted
            if (item.isCompleted) {
                item.isPrioritized = false
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

    private suspend fun update(item: FamilyListModel){
        loading = true
        updateFamilyListUseCase.execute(item = item)
        loadData(this.tabIndex, fromNetwork = false)
        loading = false
    }
}
