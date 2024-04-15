package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.extensions.toCurrencyFormat
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.FamilyListBottomSheetOpenImage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.FamilyListBottomSheetQrcode
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.FamilyListTabItemCompletedView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.FamilyListTabItemView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.FamilyListTopAppBarView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyListPage(
    viewModel: IFamilyListViewModel
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current,
            initialValue = SheetValue.PartiallyExpanded,
            confirmValueChange = {
                if (it == SheetValue.Hidden || it == SheetValue.PartiallyExpanded) {
                    viewModel.openImageSelectedItem = null
                }
                true
            },
        )
    )

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "FamilyListPage") {
        viewModel.loadData(fromNetwork = true)
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = when (viewModel.openImageSelectedItem) {
            null -> FamilyListBottomSheetQrcode(bottomSheetScaffoldState, viewModel)
            else -> FamilyListBottomSheetOpenImage(bottomSheetScaffoldState, viewModel)
        },
        content =  {
            Scaffold(
                topBar = FamilyListTopAppBarView(viewModel)
            ) { innerPadding ->

                val tabs = listOf(
                    Pair("Priorizado", viewModel.sumOfPrioritized),
                    Pair("Pendente", viewModel.sumOfPending),
                    Pair("Comprado", viewModel.sumOfCompleted),
                )
                Column(modifier = Modifier.padding(innerPadding)) {
                    TabRow(selectedTabIndex = viewModel.tabIndex.value) {
                        tabs.forEachIndexed { index, pair ->
                            Tab(
                                text = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(pair.first)
                                        Text(pair.second.toCurrencyFormat(), color = Color.Gray)
                                    }
                                },
                                selected = viewModel.tabIndex.value == index,
                                onClick = {
                                    viewModel.tabIndex = FamilyListPageTabEnum.getBy(index)
                                    coroutineScope.launch {
                                        viewModel.loadData(fromNetwork = false)
                                    }
                                },
                                icon = {
                                    when (index) {
                                        0 -> Icon(
                                            imageVector = Icons.TwoTone.ShoppingCart,
                                            contentDescription = "Priorizado"
                                        )

                                        1 -> Icon(
                                            imageVector = Icons.AutoMirrored.TwoTone.List,
                                            contentDescription = "Pendente"
                                        )

                                        2 -> Icon(
                                            imageVector = Icons.TwoTone.CheckCircle,
                                            contentDescription = "Comprado"
                                        )
                                    }
                                }
                            )
                        }
                    }

                    when (viewModel.tabIndex.value) {
                        0 -> FamilyListTabItemView(
                            viewModel = viewModel,
                            bottomSheetScaffoldState = bottomSheetScaffoldState
                        )

                        1 -> FamilyListTabItemView(
                            viewModel = viewModel,
                            bottomSheetScaffoldState = bottomSheetScaffoldState
                        )

                        2 -> FamilyListTabItemCompletedView(
                            viewModel = viewModel,
                            bottomSheetScaffoldState = bottomSheetScaffoldState
                        )
                    }
                }

            }
        }
    )
}

@Preview
@Composable
fun FamilyListPage_Preview() {
    MyApplicationTheme {
        FamilyListPage( viewModel = FamilyListViewModelMocked())
    }
}

