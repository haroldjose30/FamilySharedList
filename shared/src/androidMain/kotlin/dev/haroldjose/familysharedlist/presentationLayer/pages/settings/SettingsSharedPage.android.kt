package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.runtime.Composable

@Composable
fun SettingsPage(goBack: () -> Unit) {
    SettingsSharedPage(
        goBack = goBack
    )
}