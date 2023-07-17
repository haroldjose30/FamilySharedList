package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.runtime.Composable

@Composable
fun SettingsPage(
    goBack: () -> Unit,
    viewModel: ISettingsSharedViewModel
) {
    SettingsSharedPage(
        goBack = goBack,
        viewModel = viewModel
    )
}