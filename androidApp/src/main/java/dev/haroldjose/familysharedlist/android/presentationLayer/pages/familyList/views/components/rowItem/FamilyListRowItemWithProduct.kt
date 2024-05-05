package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components.rowItem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.domainLayer.extensions.Samples
import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyListRowItemWithProduct(
    item: FamilyListModel,
    viewModel: IFamilyListViewModel,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    val itemMutable = remember { mutableStateOf(item) }
    val nameInEditMode = remember { mutableStateOf(false) }
    val priceInEditMode = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            when {
                nameInEditMode.value -> {
                    ColumnRigthEditingName(
                        itemMutable,
                        nameInEditMode,
                        coroutineScope,
                        viewModel
                    )
                }

                priceInEditMode.value -> {
                    ColumnRigthEditingPrice(
                        itemMutable,
                        priceInEditMode,
                        coroutineScope,
                        viewModel
                    )
                }

                else -> {
                    ColumnLeftDefault(
                        itemMutable,
                        viewModel
                    )
                    ColumnRigthDefault(
                        itemMutable,
                        nameInEditMode,
                        priceInEditMode,
                        coroutineScope,
                        viewModel,
                        bottomSheetScaffoldState
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FamilyListRowItemWithProduct_Preview() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val item = Samples.FamilyList.nutella
    MyApplicationTheme {
        FamilyListRowItemWithProduct(
            item = item ,
            viewModel = FamilyListViewModelMocked(),
            bottomSheetScaffoldState = bottomSheetScaffoldState
        )
    }
}