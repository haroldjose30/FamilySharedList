package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ColumnRigthEditingName(
    item: MutableState<FamilyListModel>,
    nameInEditMode: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel
) {
    val nameTextFieldValue = remember { mutableStateOf(item.value.name) }
    Column(modifier = Modifier.padding(all = 10.dp)) {
        RowItemTopOptions(
            nameTextFieldValue
        )
        ItemRowBottomOptions(
            item,
            nameInEditMode,
            nameTextFieldValue,
            coroutineScope,
            viewModel,
        )
    }
}

@Composable
private fun RowItemTopOptions(
    nameTextFieldValue: MutableState<String>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = nameTextFieldValue.value,
            onValueChange = { nameTextFieldValue.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text("Alterando descrição") },
            maxLines = 3,
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
private fun ItemRowBottomOptions(
    item: MutableState<FamilyListModel>,
    nameInEditMode: MutableState<Boolean>,
    nameTextFieldValue: MutableState<String>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel,
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(
            onClick = {
                item.value.name = nameTextFieldValue.value
                coroutineScope.launch {
                    viewModel.updateName(uuid = item.value.uuid, name = item.value.name)
                }
                nameInEditMode.value = false
            },
        ) { Icon(Icons.Default.Check, contentDescription = "Gravar") }

        IconButton(
            onClick = { nameInEditMode.value = false }
        ) { Icon(Icons.Default.Close,contentDescription = "Cancelar" ) }
    }
}

@Preview
@Composable
fun ColumnRigthEditingName_Preview() {
    val coroutineScope = rememberCoroutineScope()
    val nameInEditMode = remember { mutableStateOf(false) }
    val viewModel = FamilyListViewModelMocked()
    val item = remember { mutableStateOf(Samples.FamilyList.nutella) }
    MyApplicationTheme {
        ColumnRigthEditingName(
            item = item,
            nameInEditMode = nameInEditMode,
            coroutineScope = coroutineScope,
            viewModel = viewModel,
        )
    }
}

