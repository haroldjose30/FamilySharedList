package dev.haroldjose.familysharedlist.android.pages.familyList

import QuantitySelectionView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FamilyListPage(
    viewModel: IFamilyListViewModel = getViewModel<FamilyListViewModel>()
) {

    //region STATE
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(viewModel.loading, {
        coroutineScope.launch {
            viewModel.loadData()
        }
    })
    val listState = rememberLazyListState()
    val checkedFilterState = remember { mutableStateOf(false) }

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

    LaunchedEffect(key1 = "FamilyListPage"){
        viewModel.loadData()
    }

    //pullRefresh modifier
    Column(Modifier.pullRefresh(pullRefreshState)) {
        Row {
            Spacer(Modifier.weight(1f))
            Text(
                text = "Lista de Compras",
                style = TextStyle(fontSize = 24.sp)
            )
            Spacer(Modifier.weight(1f))
        }
        Row(verticalAlignment =  Alignment.CenterVertically) {
            Spacer(Modifier.weight(1f))
            Text(
                text = "Pendente",
                style = TextStyle(fontSize = 12.sp)
            )
            Switch(
                checked = checkedFilterState.value,
                onCheckedChange = {
                    checkedFilterState.value = it
                    coroutineScope.launch {
                        viewModel.filterBy(completed = it)
                    }
                }
            )
            Text(
                text = "Comprados",
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(Modifier.weight(1f))
        }

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
                                if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f)
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
                Row {
                    Spacer(Modifier.weight(1f))
                    TextField(
                        value = viewModel.newItemName,
                        onValueChange = { viewModel.newItemName = it },
                        placeholder = { Text( "Informe o novo item")  },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),

                        )
                    Spacer(Modifier.weight(1f))
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
                    Spacer(Modifier.weight(1f))
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
    val checkedState = remember { mutableStateOf(item.isCompleted) }

    Card(
        backgroundColor = if (item.isCompleted) Color.Green.copy(alpha = 0.1f) else Color.Red.copy(alpha = 0.1f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(item.name, fontSize = 16.sp, modifier = Modifier.padding(10.dp))

            Row() {

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
                Column(horizontalAlignment = Alignment.CenterHorizontally,){
                    Text(
                        if (item.isCompleted) "comprado" else "pendente",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            item.isCompleted = it
                            onItemChanged(item)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreviewTaskListPage() {
    MyApplicationTheme {
        FamilyListPage(viewModel = FamilyListViewModelMocked())
    }
}