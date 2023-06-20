package dev.haroldjose.familysharedlist.android.pages.familyList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
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
): ViewModel(), IFamilyListViewModel {

    override var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    override var loading:Boolean by mutableStateOf(false)
    override var newItemName: String by mutableStateOf("")
    override var quantity: Int by mutableStateOf(1)
    override var filterByCompleted: Boolean by mutableStateOf(false)

    override suspend fun loadData() {

        loading = true
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

        e.message?.let { Log.d("showError", it) }
    }


    //TODO: change debounce to UI component not in viewModel
    var updateDebounceJob: Job? = null
    override suspend fun update(item: FamilyListModel){

        updateDebounceJob?.cancel()
        updateDebounceJob = viewModelScope.launch {
            delay(500)
            loading = true
            updateFamilyListUseCase.execute(item = item)
            loading = false
        }
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
