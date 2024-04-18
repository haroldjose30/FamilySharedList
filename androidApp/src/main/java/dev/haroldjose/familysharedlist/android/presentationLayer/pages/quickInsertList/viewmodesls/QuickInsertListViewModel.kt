package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase

class QuickInsertListViewModel(
    private val createFamilyListUseCase: CreateFamilyListUseCase
): ViewModel(), IQuickInsertListViewModel {
    override var viewState: QuickInsertListViewState = QuickInsertListViewState.Initial
    override var text:String by mutableStateOf("")
    override var goToFamilyListPage: () -> Unit = {}
    override suspend fun quickInsertItem() {

        if (text.isEmpty()) return

        val listOfItem = text.lines().mapNotNull { item ->

            //extrai os numeros
            val quantityStr = item.filter { it.isDigit() }.toIntOrNull()
            //extrai o nome
            val name = item.filter { !it.isDigit() }

            if (name.isEmpty())
                return@mapNotNull null

            return@mapNotNull FamilyListModel(
                name = name.ifEmpty { item },
                isPrioritized = true,
                quantity = quantityStr ?: 1
            )
        }
        text = ""
        viewState = QuickInsertListViewState.Loading
        try {
            createFamilyListUseCase.execute(items = listOfItem)
            goToFamilyListPage()
            QuickInsertListViewState.Success
        } catch (e: Throwable) {
            showError(e)
            return
        }
    }

    private fun showError(e: Throwable) {
        e.message?.let { Logger.d("showError", it) }
        viewState = QuickInsertListViewState.Error(
            message = e.message ?: "Erro inesperado",
            retryAction = { viewState = QuickInsertListViewState.Initial }
        )
    }
}