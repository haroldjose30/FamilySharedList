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
    override var filterByCompleted: Boolean by mutableStateOf(false)
    lateinit var accountModel: AccountModel

    override suspend fun loadData() {

        loading = true

        accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()
        familyListModels = getAllFamilyListUseCase
            .execute()
            .filter { it.isCompleted == filterByCompleted }
            .sortedBy { it.name.lowercase() }
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
        loadData()
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
        loadData()
        loading = false
    }

    override suspend fun filterBy(completed: Boolean) {
        filterByCompleted = completed
        loadData()
    }
}
