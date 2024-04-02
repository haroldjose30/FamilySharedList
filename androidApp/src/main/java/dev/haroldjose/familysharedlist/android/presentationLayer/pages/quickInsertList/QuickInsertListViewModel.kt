package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.familyList.CreateFamilyListUseCase

class QuickInsertListViewModel(
    private val createFamilyListUseCase: CreateFamilyListUseCase
): IQuickInsertListViewModel {
    override var loading:Boolean by mutableStateOf(false)
    override var text:String by mutableStateOf("")
    override var goToFamilyListPage: () -> Unit = {}
    override suspend fun quickInsertItem() {

        if (text.isEmpty()) return

        val listOfItem = text.lines().map { item ->

            //extrai os numeros
            val quantityStr = item.filter { it.isDigit() }.toIntOrNull()
            //extrai o nome
            val name = item.filter { !it.isDigit() }
            return@map FamilyListModel(
                name = name.ifEmpty { item },
                isPrioritized = true,
                quantity = quantityStr ?: 1
            )
        }
        text = ""
        loading = true
        createFamilyListUseCase.execute(items = listOfItem)
        loading = false
        goToFamilyListPage()
    }
}