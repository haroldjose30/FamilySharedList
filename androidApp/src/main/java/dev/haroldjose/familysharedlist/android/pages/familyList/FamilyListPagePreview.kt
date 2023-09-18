package dev.haroldjose.familysharedlist.android.pages.familyList

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListPage

@Preview
@Composable
fun FamilyListPagePreview() {
    FamilyListPage(
        viewModel = FamilyListViewModelMocked(),
        goToSetting = {}
    )
}