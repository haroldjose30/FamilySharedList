package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import QuantitySelectionView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Switch
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.presentationLayer.Components.QrCodeScannerIconView
import kotlinx.coroutines.launch

@Composable
internal fun FamilyListSharedPage(
    viewModel: IFamilyListSharedViewModel,
    goToSetting: () -> Unit,
    goToScanner: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var tabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = "FamilyListPage"){
        viewModel.loadData(tabIndex)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Compras"
                    )
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
    ) {
        val tabs = listOf("Priorizado", "Pendente", "Comprado")
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index
                            coroutineScope.launch {
                                viewModel.loadData(tabIndex)
                            }
                        },
                        icon = {
                            when (index) {
                                0 -> Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                                1 -> Icon(imageVector = Icons.Default.List, contentDescription = null)
                                2 -> Icon(imageVector = Icons.Default.Check, contentDescription = null)
                            }
                        }
                    )
                }
            }
            when (tabIndex) {
                0 -> FamilyListSharedTab(
                    viewModel = viewModel,
                    tabIndex = tabIndex,
                    goToScanner = goToScanner
                )
                1 -> FamilyListSharedTab(
                    viewModel = viewModel,
                    tabIndex = tabIndex,
                    goToScanner = goToScanner
                )
                2 -> FamilyListSharedTab(
                    viewModel = viewModel,
                    tabIndex = tabIndex,
                    goToScanner = goToScanner
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FamilyListSharedTab(
    viewModel: IFamilyListSharedViewModel,
    tabIndex: Int,
    goToScanner: () -> Unit
) {

    //region STATE
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(viewModel.loading, {
        coroutineScope.launch {
            viewModel.loadData(tabIndex)
        }
    })
    val listState = rememberLazyListState()

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

    Column(Modifier.pullRefresh(pullRefreshState)) {
        if (viewModel.loading) {

            Row {
                Spacer(Modifier.weight(1f))
                //standard Pull-Refresh indicator. You can also use a custom indicator
                PullRefreshIndicator(viewModel.loading, pullRefreshState)
                Spacer(Modifier.weight(1f))
            }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            items(
                items = viewModel.familyListModels,
                key = { item -> item.uuid },
                itemContent = { item ->
                    val currentItem by rememberUpdatedState(item)
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            val result = when (it) {
                                DismissValue.Default -> {
                                    false
                                }

                                DismissValue.DismissedToStart -> {
                                    onItemRemoved(currentItem)
                                    true
                                }

                                DismissValue.DismissedToEnd -> {
                                    item.isCompleted = true
                                    onItemChanged(item)
                                    false
                                }
                            }

                            result
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .padding(vertical = Dp(1f)),
                        directions = setOf(
                            //DismissDirection.StartToEnd, //disabled
                            DismissDirection.EndToStart
                        ),
                        dismissThresholds = { direction ->
                            FractionalThreshold(
                                if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                            )
                        },
                        background = {
                            SwipeBackground(dismissState)
                        },
                        dismissContent = {

                            FamilyListRow(
                                item = item,
                                onItemChanged = onItemChanged
                            )
                        }
                    )
                }
            )

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { goToScanner() }
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
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SwipeBackground(dismissState: DismissState) {

    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Green
            DismissValue.DismissedToStart -> Color.Red
        }
    )

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }

    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
    }

    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = Dp(20f)),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Delete Icon",
            modifier = Modifier.scale(scale)
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

    val backgroundColor: Color = if (item.isCompleted)
        Color.Green.copy(alpha = 0.2f)
    else if (!item.isCompleted && item.isPriorized) {
        Color.Red.copy(alpha = 0.2f)
    } else {
        Color.Yellow.copy(alpha = 0.2f)
    }

    Card(
        backgroundColor = backgroundColor,
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