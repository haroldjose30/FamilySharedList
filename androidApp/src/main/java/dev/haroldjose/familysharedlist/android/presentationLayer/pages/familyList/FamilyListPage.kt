package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.android.presentationLayer.components.QrCodeScannerIconView
import dev.haroldjose.familysharedlist.android.presentationLayer.components.QuantitySelectionView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.barcodeScanner.QrScannerScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FamilyListPage(
    viewModel: IFamilyListViewModel,
    goToSetting: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var tabIndex: FamilyListPageTabEnum by remember { mutableStateOf(FamilyListPageTabEnum.PENDING) }

    LaunchedEffect(key1 = "FamilyListPage"){
        viewModel.loadData(tabIndex)
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween
                    ) {
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                        bottomSheetScaffoldState.bottomSheetState.partialExpand()
                                    } else {
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                QrCodeScannerIconView(),
                                contentDescription = "Scanner"
                            )
                        }
                        TextField(
                            value = viewModel.newItemName,
                            onValueChange = { viewModel.newItemName = it },
                            placeholder = { Text("Informe o novo item") },
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = true,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.add()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.AddCircle,
                                contentDescription = "Add"
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                    Logger.d("FamilyListSharedPage","Scanner open")
                    QrScannerScreen(
                        modifier = Modifier.fillMaxWidth(),
                        onQrCodeScanned = { barcodeScanned ->
                            Logger.d("FamilyListSharedPage","barcodeScanned $barcodeScanned")
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.partialExpand()
                                viewModel.addBy(barcode = barcodeScanned)
                            }
                        }
                    )
                }
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Lista de Compras")
                    },
                    actions = {
                        IconButton(onClick = { goToSetting() }) {
                            Icon(
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = "Configurações"
                            )
                        }
                    },
                )
            }
        ) { innerPadding ->
            val tabs = listOf("Priorizado", "Pendente", "Comprado")
            Column(modifier = Modifier.padding(innerPadding)) {
                TabRow(selectedTabIndex = tabIndex.value) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex.value == index,
                            onClick = {
                                tabIndex = FamilyListPageTabEnum.getBy(index)
                                coroutineScope.launch {
                                    viewModel.loadData(tabIndex)
                                }
                            },
                            icon = {
                                when (index) {
                                    0 -> Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                                    1 -> Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = null)
                                    2 -> Icon(imageVector = Icons.Default.Check, contentDescription = null)
                                }
                            }
                        )
                    }
                }
                FamilyListTab(
                    viewModel = viewModel,
                    tabIndex = tabIndex
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FamilyListTab(
    viewModel: IFamilyListViewModel,
    tabIndex: FamilyListPageTabEnum
) {

    //region STATE

    val coroutineScope = rememberCoroutineScope()
    //TODO: PULL REFRESH
//    val pullRefreshState = rememberPullRefreshState(viewModel.loading, {
//        coroutineScope.launch {
//            viewModel.loadData(tabIndex)
//        }
//    })
    val listState = rememberLazyListState()
    //TODO: Modal Sheet
//    val modalSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
//        skipHalfExpanded = true
//    )

    //endregion

    //region FUNCTIONS
    val onItemChanged: (FamilyListModel) -> Unit = {
        coroutineScope.launch {
            viewModel.update(item = it)
        }
    }

    val onItemRemoved: (FamilyListModel) -> Unit = {
        coroutineScope.launch {
            viewModel.remove(item = it)
        }
    }
    //endregion

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 128.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = viewModel.familyListModels,
            key = { item -> item.uuid },
            itemContent = { item ->
                FamilyListRow(
                    item = item,
                    onItemChanged = onItemChanged
                )
            }
        )
    }
}

@Composable
fun FamilyListRow(
    item: FamilyListModel,
    onItemChanged: ((FamilyListModel) -> Unit)
) {
    val checkedStateIsCompleted = remember { mutableStateOf(item.isCompleted) }
    val checkedStateIsPriorized = remember { mutableStateOf(item.isPriorized) }

    //val backgroundColor: Color = if (item.isCompleted)
    //    Color.Green.copy(alpha = 0.2f)
    //else if (!item.isCompleted && item.isPriorized) {
    //    Color.Red.copy(alpha = 0.2f)
    //} else {
    //    Color.Yellow.copy(alpha = 0.2f)
    //}

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(item.name, fontSize = 16.sp, modifier = Modifier.padding(10.dp))

            Row {

                QuantitySelectionView(
                    value = item.quantity,
                    minValue = 1,
                    maxValue = 50,
                    onValueChanged = {
                        item.quantity = it
                        onItemChanged(item)
                    }
                )
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        "Priorizado",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Switch(
                        checked = checkedStateIsPriorized.value,
                        onCheckedChange = {
                            checkedStateIsPriorized.value = it
                            item.isPriorized = it
                            onItemChanged(item)
                        }
                    )
                }

                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        if (item.isCompleted) "comprado" else "pendente",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Switch(
                        checked = checkedStateIsCompleted.value,
                        onCheckedChange = {
                            checkedStateIsCompleted.value = it
                            item.isCompleted = it
                            onItemChanged(item)
                        }
                    )
                }
            }
        }
    }
}