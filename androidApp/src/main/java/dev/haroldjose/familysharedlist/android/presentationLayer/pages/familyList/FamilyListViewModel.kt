package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.Logger
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
    private var familyListModelsCompleted: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var loading:Boolean by mutableStateOf(false)
    override var newItemName: String by mutableStateOf("")
    override var quantity: Int by mutableStateOf(1)
    override var tabIndex: FamilyListPageTabEnum by mutableStateOf(FamilyListPageTabEnum.PENDING)
    private lateinit var accountModel: AccountModel

    override suspend fun loadData(tabIndex: FamilyListPageTabEnum) {
        this.tabIndex = tabIndex
        loading = true

        accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()

        familyListModelsCompleted = getAllFamilyListUseCase.execute().sortedBy { it.name.lowercase() }
        familyListModels = when (tabIndex) {
            FamilyListPageTabEnum.PRIORIZED -> familyListModelsCompleted
                .filter { !it.isCompleted && it.isPriorized }

            FamilyListPageTabEnum.PENDING -> familyListModelsCompleted
                .filter { !it.isCompleted }

            FamilyListPageTabEnum.COMPLETED -> familyListModelsCompleted
                .filter { it.isCompleted }
        }

        loading = false
    }

    override suspend fun add() {

        if (newItemName.isEmpty())
            return

        if (getPlatform().isDebug && newItemName.startsWith("debug", ignoreCase = true)) {
            newItemName = newItemName.replace(oldValue = "debug", newValue = "", ignoreCase = true)
            addBy(barcode = newItemName)
            newItemName = ""
            return
        }

        val item = FamilyListModel(
            name = newItemName,
            quantity = quantity,
            isPriorized = this.tabIndex.isPriorized(),
            isCompleted = false
        )
        newItemName = ""
        loading = true
        createFamilyListUseCase.execute(item = item)
        loadData(this.tabIndex)
    }

    override suspend fun addBy(barcode: String) {
        Logger.d("FamilyListSharedViewModel", "barcode: $barcode")
        if (barcode.isEmpty())
            return

        loading = true
        val productModelFounded = getProductByCodeUseCase.execute(code = barcode)

        productModelFounded?.let { productModel ->

            //verifica se o item ja foi adicionado
            familyListModelsCompleted.firstOrNull { familyListModel ->
                familyListModel.name.compareTo(productModel.productName, ignoreCase = true) == 0
            }?.let { itemFounded ->

                //se o item estiver concluido, altera para pendente e muda a tab
                if (itemFounded.isCompleted) {
                    itemFounded.isCompleted = false
                    itemFounded.isPriorized = this.tabIndex.isPriorized()
                    update(item = itemFounded)
                    if (this.tabIndex.isCompleted())
                        this.tabIndex = FamilyListPageTabEnum.PENDING

                    loadData(this.tabIndex)
                    return
                } else {
                    //se nao estiver concluido aumenta a quantidade do mesmo em 1
                    itemFounded.isPriorized = this.tabIndex.isPriorized()
                    itemFounded.quantity += 1
                    update(item = itemFounded)
                    loadData(this.tabIndex)
                    return
                }
            }

            val item = FamilyListModel(
                name = productModel.productName,
                quantity = quantity,
                isPriorized = this.tabIndex == FamilyListPageTabEnum.PRIORIZED,
                isCompleted = false
            )

            createFamilyListUseCase.execute(item = item)
            Logger.d("FamilyListSharedViewModel", "createFamilyListUseCase executed")
            loadData(this.tabIndex)
            Logger.d("FamilyListSharedViewModel", "loadData executed")
        }
    }

    override fun showError(e: Throwable) {
        //TODO: implement log in shared module
        //e.message?.let { Log.d("showError", it) }
    }

    override suspend fun update(item: FamilyListModel){

        loading = true
        updateFamilyListUseCase.execute(item = item)
        loading = false
    }

    override suspend fun remove(item: FamilyListModel){

        loading = true
        deleteFamilyListUseCase.execute(uuid = item.uuid)
        loadData(this.tabIndex)
        loading = false
    }
}
