package dev.haroldjose.familysharedlist.android.pages.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.SettingsPage
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.SettingsSharedViewModelMocked

@Preview
@Composable
fun SettingsPagePreview() {
    SettingsPage(
        viewModel = SettingsSharedViewModelMocked(),
        goBack = {}
    )
}