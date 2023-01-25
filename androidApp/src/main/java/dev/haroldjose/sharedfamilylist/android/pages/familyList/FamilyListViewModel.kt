package dev.haroldjose.sharedfamilylist.android.pages.familyList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.haroldjose.sharedfamilylist.dependencyInjection.ServiceLocator
import dev.haroldjose.sharedfamilylist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FamilyListViewModel(): ViewModel() {

    var familyListModels: List<FamilyListModel> by mutableStateOf(arrayListOf())
    var loading:Boolean by mutableStateOf(false)
    var newItemName: String by mutableStateOf("")
    var quantity: Int by mutableStateOf(1)

    //Todo: implement DI
    private val getAllFamilyListUseCase = ServiceLocator.getAllFamilyListUseCase
    private val createFamilyListUseCase = ServiceLocator.createFamilyListUseCase
    private val updateFamilyListUseCase = ServiceLocator.updateFamilyListUseCase
    private val deleteFamilyListUseCase = ServiceLocator.deleteFamilyListUseCase

    suspend fun loadData() {

        loading = true
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