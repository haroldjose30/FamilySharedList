package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.components.CurrencyAmountInput
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ColumnRigthEditingPrice(
    item: MutableState<FamilyListModel>,
    priceInEditMode: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel
) {
    Column(modifier = Modifier.padding(all = 10.dp)) {
        RowItemTopOptions(
            item
        )
        ItemRowBottomOptions(
            item,
            priceInEditMode,
            coroutineScope,
            viewModel,
        )
    }
}

@Composable
private fun RowItemTopOptions(
    item: MutableState<FamilyListModel>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CurrencyAmountInput(item.value.price) {
            item.value.price = it
        }
    }
}

@Composable
private fun ItemRowBottomOptions(
    item: MutableState<FamilyListModel>,
    priceInEditMode: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel,
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.updatePrice(uuid = item.value.uuid, price = item.value.price)
                }
                priceInEditMode.value = false
            },
        ) { Icon(Icons.Default.Check, contentDescription = "Gravar") }

        IconButton(
            onClick = { priceInEditMode.value = false }
        ) { Icon(Icons.Default.Close,contentDescription = "Cancelar" ) }
    }
}

@Preview
@Composable
fun ColumnRigthEditingPrice_Preview() {
    val coroutineScope = rememberCoroutineScope()
    val priceInEditMode = remember { mutableStateOf(false) }
    val viewModel = FamilyListViewModelMocked()
    val item = remember { mutableStateOf(Samples.FamilyList.nutella) }
    MyApplicationTheme {
        ColumnRigthEditingPrice(
            item = item,
            priceInEditMode = priceInEditMode,
            coroutineScope = coroutineScope,
            viewModel = viewModel,
        )
    }
}

