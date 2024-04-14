package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.extensions.toCurrencyFormat
import dev.haroldjose.familysharedlist.android.presentationLayer.components.QuantitySelectionView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnRigthDefault(
    item: MutableState<FamilyListModel>,
    nameInEditMode: MutableState<Boolean>,
    priceInEditMode: MutableState<Boolean>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    Column(modifier = Modifier.padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)) {
        RowItemTopOptions(
            item,
            nameInEditMode
        )
        RowItemMiddleOptions(
            item,
            priceInEditMode,
        )
        ItemRowBottomOptions(
            item,
            coroutineScope,
            viewModel,
            bottomSheetScaffoldState
        )
    }
}

@Composable
private fun RowItemTopOptions(
    item: MutableState<FamilyListModel>,
    nameInEditMode: MutableState<Boolean>
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            item.value.name,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1.0f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            nameInEditMode.value = true
                        },
                    )
                },
            maxLines = 3
        )
    }
}

@Composable
private fun RowItemMiddleOptions(
    item: MutableState<FamilyListModel>,
    priceInEditMode: MutableState<Boolean>,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            item.value.price.toCurrencyFormat(),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1.0f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            priceInEditMode.value = true
                        },
                    )
                },
            maxLines = 3
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemRowBottomOptions(
    item: MutableState<FamilyListModel>,
    coroutineScope: CoroutineScope,
    viewModel: IFamilyListViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    Row(verticalAlignment = Alignment.Bottom) {
        if (!item.value.isCompleted) {
            QuantitySelectionView(
                value = item.value.quantity,
                minValue = 1,
                maxValue = 50,
                coroutineScope = coroutineScope,
                onValueChanged = {
                    item.value.quantity = it
                    coroutineScope.launch {
                        viewModel.updateQuantity(uuid = item.value.uuid, quantity = it)
                    }
                }
            )
        }

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.selectedItemUuid = item.value.uuid
                    if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                        bottomSheetScaffoldState.bottomSheetState.partialExpand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.qr_code_scanner),
                contentDescription = "Scanner"
            )
        }

        if (!item.value.isCompleted) {
            IconButton(
                onClick = {
                    item.value.isPrioritized = !item.value.isPrioritized
                    coroutineScope.launch {
                        viewModel.updateIsPrioritized(
                            uuid = item.value.uuid,
                            isPrioritized = item.value.isPrioritized
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = if (item.value.isPrioritized) Icons.TwoTone.ShoppingCart else Icons.Outlined.ShoppingCart,
                    contentDescription = "Priorizado",
                    tint = Color.DarkGray
                )
            }
        }

        IconButton(
            onClick = {
                item.value.isCompleted = !item.value.isCompleted
                coroutineScope.launch {
                    viewModel.updateIsCompleted(uuid = item.value.uuid, isCompleted = item.value.isCompleted)
                }
            }
        ) {
            Icon(
                imageVector = if (item.value.isCompleted) Icons.AutoMirrored.TwoTone.List else Icons.TwoTone.CheckCircle,
                contentDescription = "Comprado",
                tint = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ColumnRigthDefault_Preview() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val nameInEditMode = remember { mutableStateOf(false) }
    val priceInEditMode = remember { mutableStateOf(false) }
    val viewModel = FamilyListViewModelMocked()
    val item = remember {mutableStateOf( Samples.FamilyList.nutella) }
    item.value.isCompleted = false
    item.value.isPrioritized = false
    MyApplicationTheme {
        ColumnRigthDefault(
            item = item,
            nameInEditMode = nameInEditMode,
            priceInEditMode = priceInEditMode,
            coroutineScope = coroutineScope,
            viewModel = viewModel,
            bottomSheetScaffoldState = bottomSheetScaffoldState
        )
    }
}

