package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.components.QuantitySelectionView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import kotlinx.coroutines.launch

@Composable
fun FamilyListRowItem(
    item: FamilyListModel,
    viewModel: IFamilyListViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val isCompleted = remember { mutableStateOf(item.isCompleted) }
    val isPrioritized = remember { mutableStateOf(item.isPrioritized) }
    val nameInEditMode = remember { mutableStateOf(false) }
    val nameTextFieldValue = remember { mutableStateOf(item.name) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            )  {
                if (nameInEditMode.value) {
                    OutlinedTextField(
                        value = nameTextFieldValue.value,
                        onValueChange = { nameTextFieldValue.value = it },
                        modifier = Modifier.fillMaxWidth(),
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
                } else {
                    Text(item.name, fontSize = 16.sp, modifier = Modifier.padding(10.dp).weight(1.0f), maxLines = 3)
                    IconButton(
                        onClick = { nameInEditMode.value = true }
                    ) { Icon(Icons.Default.Edit,contentDescription = "Editar", modifier =  Modifier.defaultMinSize(36.dp)) }
                }
            }
            if (nameInEditMode.value) {
                Row {
                    Spacer(modifier = Modifier.weight(1.0f))
                    IconButton(
                        onClick = {
                            item.name = nameTextFieldValue.value
                            coroutineScope.launch {
                                viewModel.updateName(uuid = item.uuid, name= nameTextFieldValue.value)
                            }
                            nameInEditMode.value = false
                        },
                    ) { Icon(Icons.Default.Check, contentDescription = "Gravar") }

                    IconButton(
                        onClick = { nameInEditMode.value = false }
                    ) { Icon(Icons.Default.Close,contentDescription = "Cancelar" ) }
                }
            } else {
                Row {

                    if (!isCompleted.value) {
                        QuantitySelectionView(
                            value = item.quantity,
                            minValue = 1,
                            maxValue = 50,
                            onValueChanged = {
                                item.quantity = it
                                coroutineScope.launch {
                                    viewModel.updateQuantity(uuid = item.uuid, quantity = it)
                                }
                            }
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                item.isPrioritized = !item.isPrioritized
                                isPrioritized.value = item.isPrioritized
                                coroutineScope.launch {
                                    viewModel.updateIsPrioritized(uuid = item.uuid, isPrioritized = isPrioritized.value)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isPrioritized.value) Icons.TwoTone.ShoppingCart else  Icons.Outlined.ShoppingCart,
                                contentDescription = "Priorizado",
                            )
                        }

                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            item.isCompleted = !item.isCompleted
                            isCompleted.value = item.isCompleted
                            coroutineScope.launch {
                                viewModel.updateIsCompleted(uuid = item.uuid, isCompleted = isCompleted.value)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isCompleted.value) Icons.AutoMirrored.TwoTone.List else  Icons.TwoTone.CheckCircle,
                            contentDescription = "Comprado",
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FamilyListRowItem_Preview() {
    MyApplicationTheme {
        FamilyListRowItem(
            item = FamilyListModel(
                name = "Item no preview",
                isCompleted = false,
                isPrioritized = false,
                quantity = 2
            ),
            viewModel = FamilyListViewModelMocked()
        )
    }
}
