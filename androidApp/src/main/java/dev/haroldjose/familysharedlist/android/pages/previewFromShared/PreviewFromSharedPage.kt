package dev.haroldjose.familysharedlist.android.pages.previewFromShared

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.android.pages.settings.SettingsSharedViewModelMocked
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.SettingsPage

@Preview
@Composable
fun DefaultPreviewFromSharedPage() {
    SettingsPage(
        goBack = {},
        viewModel = SettingsSharedViewModelMocked()
    )
}