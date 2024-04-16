package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.INavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.NavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.NavigatorViewModelMock
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.IQuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.QuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.QuickInsertListViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.views.QuickInsertListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.SettingsPage
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigatorView(
    navigatorViewModel: INavigatorViewModel = koinViewModel<NavigatorViewModel>(),
    quickInsertListViewModel: IQuickInsertListViewModel = koinViewModel<QuickInsertListViewModel>(),
    settingsViewModel: ISettingsViewModel = koinViewModel<SettingsViewModel>(),
    familyListViewModel: IFamilyListViewModel = koinViewModel<FamilyListViewModel>()
) {
    val navController: NavHostController = rememberNavController()

    quickInsertListViewModel.goToFamilyListPage = { navController.navigate(ViewRouter.FAMILY_LIST.value) }
    settingsViewModel.goBack = { navController.navigate(ViewRouter.FAMILY_LIST.value) }
    familyListViewModel.goToSetting = { navController.navigate(ViewRouter.SETTINGS.value) }
    familyListViewModel.goToQuickInsert = { navController.navigate(ViewRouter.QUICK_INSERT.value) }

    LaunchedEffect(key1 = "NavigatorView") {
        navigatorViewModel.checkIfNeedToCreateNewAccount()
    }

    NavHost(navController = navController, startDestination = ViewRouter.FAMILY_LIST.value) {
        composable(ViewRouter.FAMILY_LIST.value) {
            FamilyListPage(viewModel = familyListViewModel)
        }
        composable(ViewRouter.SETTINGS.value) {
            SettingsPage(viewModel = settingsViewModel)
        }
        composable(ViewRouter.QUICK_INSERT.value) {
            QuickInsertListPage(viewModel = quickInsertListViewModel)
        }
    }
}

private enum class ViewRouter(val value: String) {
    FAMILY_LIST("family_list"),
    SETTINGS("setting"),
    QUICK_INSERT("quick_insert"),
}

@Preview
@Composable
fun FamilyListPage_Preview() {
    MyApplicationTheme {
        NavigatorView(
            navigatorViewModel = NavigatorViewModelMock(),
            quickInsertListViewModel = QuickInsertListViewModelMocked(),
            settingsViewModel = SettingsViewModelMocked(),
            familyListViewModel= FamilyListViewModelMocked()
        )
    }
}


