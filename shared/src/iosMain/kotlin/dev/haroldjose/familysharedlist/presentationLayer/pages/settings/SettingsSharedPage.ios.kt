package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController

fun SettingsPageViewController(): UIViewController =
    Application("Example Application") {
        SettingsSharedPage()
    }
