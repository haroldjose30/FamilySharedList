package dev.haroldjose.familysharedlist.presentationLayer.pages.familyList

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun FamilyListPageViewController(
    viewModel: IFamilyListSharedViewModel,
    goToSetting: () -> Unit,
    goToScanner: () -> Unit
): UIViewController {
    return ComposeUIViewController {
        FamilyListSharedPage(
            viewModel = viewModel,
            goToSetting = goToSetting
        )
    }
}

