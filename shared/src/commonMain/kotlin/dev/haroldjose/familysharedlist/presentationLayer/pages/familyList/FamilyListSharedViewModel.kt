package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase

class FamilyListSharedViewModel(
    private val getAllFamilyListUseCase: GetAllFamilyListUseCase,
    private val createFamilyListUseCase: CreateFamilyListUseCase,
    private val updateFamilyListUseCase: UpdateFamilyListUseCase,
    private val deleteFamilyListUseCase: DeleteFamilyListUseCase,
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
): IFamilyListSharedViewModel {

    override var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var loading:Boolean by mutableStateOf(false)
    override var newItemName: String by mutableStateOf("")
    override var quantity: Int by mutableStateOf(1)
    override var tabIndex: Int by mutableStateOf(0)
    private lateinit var accountModel: AccountModel

    override suspend fun loadData(tabIndex: Int) {
        this.tabIndex = tabIndex
        loading = true

        accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()

        familyListModels = when (tabIndex) {
            0 -> getAllFamilyListUseCase
                .execute()
                .filter {
                    !it.isCompleted && it.isPriorized
                }
                .sortedBy { it.name.lowercase() }
            1 -> getAllFamilyListUseCase
                .execute()
                .filter { !it.isCompleted }
                .sortedBy { it.name.lowercase() }
            2 -> getAllFamilyListUseCase
                .execute()
                .filter { it.isCompleted }
                .sortedBy { it.name.lowercase() }

            else -> {
                getAllFamilyListUseCase
                    .execute()
                    .sortedBy { it.name.lowercase() }
            }
        }

        loading = false
    }

    override suspend fun add() {

        if (newItemName.isEmpty())
            return

        val item = FamilyListModel(
            name = newItemName,
            quantity = quantity
        )
        newItemName = ""
        loading = true
        createFamilyListUseCase.execute(item = item)
        loadData(this.tabIndex)
    }

    override suspend fun addBy(barcode: String) {
        if (barcode.isEmpty())
            return

        //TODO: create logic to search product by barcode

        val item = FamilyListModel(
            name = barcode,
            quantity = quantity
        )

        loading = true
        createFamilyListUseCase.execute(item = item)
        loadData(this.tabIndex)
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
