package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import androidx.compose.runtime.Composable

@Composable
fun FamilyListPage(
    viewModel: IFamilyListSharedViewModel,
    goToSetting: () -> Unit
) {
    FamilyListSharedPage(
        viewModel = viewModel,
        goToSetting = goToSetting
    )
}