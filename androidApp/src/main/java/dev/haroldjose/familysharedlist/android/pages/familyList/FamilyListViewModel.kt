package dev.haroldjose.familysharedlist.android.pages.familyList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.DeleteFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.GetAllFamilyListUseCase
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.UpdateFamilyListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FamilyListViewModel(
    private val getAllFamilyListUseCase: GetAllFamilyListUseCase,
    private val createFamilyListUseCase: CreateFamilyListUseCase,
    private val updateFamilyListUseCase: UpdateFamilyListUseCase,
    private val deleteFamilyListUseCase: DeleteFamilyListUseCase,
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
): ViewModel() {

    var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    var loading:Boolean by mutableStateOf(false)
    var newItemName: String by mutableStateOf("")
    var quantity: Int by mutableStateOf(1)
    lateinit var accountModel: AccountModel

    suspend fun loadData() {

        loading = true

        accountModel = getOrCreateAccountFromLocalUuidUseCase.execute()
        familyListModels = getAllFamilyListUseCase.execute()
        loading = false
    }

    suspend fun add() {

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

    fun showError(e: Throwable) {

        e.message?.let { Log.d("showError", it) }
    }


    //TODO: change debounce to UI component not in viewModel
    private var updateDebounceJob: Job? = null
    suspend fun update(item: FamilyListModel){

        updateDebounceJob?.cancel()
        updateDebounceJob = viewModelScope.launch {
            delay(500)
            loading = true
            updateFamilyListUseCase.execute(item = item)
            loading = false
        }
    }

    suspend fun remove(item: FamilyListModel){

        loading = true
        deleteFamilyListUseCase.execute(uuid = item.uuid)
        loadData()
        loading = false
    }
}
