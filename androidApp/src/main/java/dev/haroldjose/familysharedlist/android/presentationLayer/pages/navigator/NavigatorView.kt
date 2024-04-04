package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.IQuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.QuickInsertListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.components.SettingsPage
import org.koin.compose.koinInject

@Composable
fun NavigatorView(
    viewModel: INavigatorViewModel
) {
    val navController: NavHostController = rememberNavController()

    LaunchedEffect(key1 = "NavigatorView") {
        viewModel.checkIfNeedToCreateNewAccount()
    }

    NavHost(navController = navController, startDestination = ViewRouter.QUICK_INSERT.value) {
        composable(ViewRouter.FAMILY_LIST.value) {
            FamilyListPage(navController)
        }
        composable(ViewRouter.SETTINGS.value) {
            SettingsPage(navController)
        }
        composable(ViewRouter.QUICK_INSERT.value) {
            QuickInsertListPage(navController)
        }
    }
}
@Composable
private fun QuickInsertListPage(navController: NavHostController) {
    val viewModel: IQuickInsertListViewModel = koinInject()
    viewModel.goToFamilyListPage = { navController.navigate(ViewRouter.FAMILY_LIST.value) }
    QuickInsertListPage(viewModel)
}

@Composable
private fun SettingsPage(navController: NavHostController) {
    val viewModel: ISettingsViewModel = koinInject()
    viewModel.goBack = { navController.navigate(ViewRouter.FAMILY_LIST.value) }
    SettingsPage(viewModel)
}

@Composable
private fun FamilyListPage(navController: NavHostController) {
    val viewModel: IFamilyListViewModel = koinInject()
    viewModel.goToSetting = { navController.navigate(ViewRouter.SETTINGS.value) }
    viewModel.goToQuickInsert = { navController.navigate(ViewRouter.QUICK_INSERT.value) }
    FamilyListPage(viewModel)
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
        NavigatorView(viewModel = NavigatorViewModelMock())
    }
}


