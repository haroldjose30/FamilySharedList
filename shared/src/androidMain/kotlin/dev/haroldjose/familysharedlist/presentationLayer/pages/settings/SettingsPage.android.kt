package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.runtime.Composable

@Composable
fun SettingsPage(
    viewModel: ISettingsSharedViewModel,
    goBack: () -> Unit
) {
    SettingsSharedPage(
        goBack = goBack,
        viewModel = viewModel
    )
}