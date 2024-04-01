package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.components.FamilyListBottomSheetContent
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.components.FamilyListTabView
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListViewModelMocked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyListPage(
    viewModel: IFamilyListViewModel
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = FamilyListBottomSheetContent(bottomSheetScaffoldState, viewModel),
        content = FamilyListTabView(viewModel)
    )
}

@Preview
@Composable
fun FamilyListPage_Preview() {
    MyApplicationTheme {
        FamilyListPage( viewModel = FamilyListViewModelMocked())
    }
}

