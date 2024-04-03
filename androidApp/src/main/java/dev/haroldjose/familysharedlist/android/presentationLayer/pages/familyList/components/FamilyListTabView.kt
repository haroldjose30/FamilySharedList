package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.List
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPageTabEnum
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListViewModelMocked
import kotlinx.coroutines.launch

@Composable
fun FamilyListTabView(
    viewModel: IFamilyListViewModel
): @Composable (PaddingValues) -> Unit {

    val coroutineScope = rememberCoroutineScope()
    var tabIndex: FamilyListPageTabEnum by remember { mutableStateOf(FamilyListPageTabEnum.PENDING) }

    LaunchedEffect(key1 = "FamilyListPage") {
        viewModel.loadData(tabIndex, fromNetwork = true)
    }

    return {
        Scaffold(
            topBar = FamilyListTopBarView(viewModel)
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
                                    viewModel.loadData(tabIndex, fromNetwork = false)
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
                FamilyListTabItemView(
                    viewModel = viewModel,
                    tabIndex = tabIndex
                )
            }
        }
    }
}

@Preview
@Composable
fun FamilyListTabView_Preview() {
    MyApplicationTheme {
        FamilyListPage( viewModel = FamilyListViewModelMocked())
    }
}

