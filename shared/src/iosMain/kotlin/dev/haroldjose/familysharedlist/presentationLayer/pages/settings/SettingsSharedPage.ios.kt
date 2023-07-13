package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun SettingsPageViewController(goBack: () -> Unit): UIViewController {
    return ComposeUIViewController {
        SettingsSharedPage(goBack = goBack)
    }
}

