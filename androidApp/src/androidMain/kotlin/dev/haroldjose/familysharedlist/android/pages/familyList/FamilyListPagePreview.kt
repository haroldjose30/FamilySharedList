package dev.haroldjose.familysharedlist.android.pages.familyList

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListSharedViewModelMocked

@Preview(name = "teste")
@Composable
fun FamilyListPagePreview() {
    FamilyListPage(
        viewModel = FamilyListSharedViewModelMocked(),
        goToSetting = {},
    )
}