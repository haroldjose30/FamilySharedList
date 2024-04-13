package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components

import android.widget.Space
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyListTabItemView(
    viewModel: IFamilyListViewModel,
    tabIndex: FamilyListPageTabEnum,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {

    //region STATE

    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.loadData(tabIndex, fromNetwork = true)
            state.endRefresh()
        }
    }
    val scaleFraction = if (state.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(state.progress).coerceIn(0f, 1f)

    val listState = rememberLazyListState()

    //endregion

    if (viewModel.familyListModelsFiltered.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Empty List"
                )
                Text(
                    text = "Nenhum item encontrado"
                )
            }
        }
    } else {
        Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 128.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (!state.isRefreshing) {
                    items(
                        items = viewModel.familyListModelsFiltered,
                        key = { item -> item.uuid },
                        itemContent = { item ->
                            FamilyListRowSwipeToDismiss(
                                item = item,
                                viewModel = viewModel,
                                bottomSheetScaffoldState = bottomSheetScaffoldState
                            )
                        }
                    )
                }

            }
            PullToRefreshContainer(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
                state = state,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FamilyListTabItemView_Preview() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    MyApplicationTheme {
        FamilyListTabItemView(
            viewModel = FamilyListViewModelMocked(),
            tabIndex = FamilyListPageTabEnum.PRIORIZED,
            bottomSheetScaffoldState = bottomSheetScaffoldState
        )
    }
}
