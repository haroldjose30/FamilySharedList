package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun SettingsPageViewController(
    goBack: () -> Unit,
    viewModel: ISettingsSharedViewModel
): UIViewController {
    return ComposeUIViewController {
        SettingsSharedPage(
            goBack = goBack,
            viewModel = viewModel
        )
    }
}

