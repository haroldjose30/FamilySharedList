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
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
    viewModel: FamilyListViewModel = getViewModel()
) {

    //region STATE
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(viewModel.loading, {
        coroutineScope.launch {
            viewModel.loadData()
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


            items(viewModel.familyListModels) { familyList ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    onItemRemoved(familyList)
                }

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

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
                    },
                    dismissContent = {

                        FamilyListRow(
                            item = familyList,
                            onItemChanged = onItemChanged
                        )
                    }
                )
            }

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
fun FamilyListRow(
    item: FamilyListModel,
    onItemChanged: ((FamilyListModel) -> Unit)
) {
    val checkedState = remember { mutableStateOf(item.isCompleted) }
    Card(modifier = Modifier
        .fillMaxWidth()) {
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

@Preview(showBackground = false)
@Composable
fun DefaultPreviewTaskListPage() {
    MyApplicationTheme {
        //FamilyListPage(FamilyListViewModelPreview())
    }
}
