package dev.haroldjose.familysharedlist.android.presentationLayer.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.SettingsPage
import org.koin.compose.koinInject

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    val  familyListViewModel: IFamilyListViewModel = koinInject()
    familyListViewModel.goToSetting = {
        navController.navigate(ViewRouter.SETTINGS.value)
    }
    familyListViewModel.goToEditItem = {
        Logger.d("NavigationView","familyListViewModel.goToEditItem called")
    }
    NavHost(navController = navController, startDestination = "home") {
        composable(ViewRouter.HOME.value) {
            FamilyListPage(viewModel = familyListViewModel)
        }
        composable(ViewRouter.SETTINGS.value) {
            SettingsPage(
                viewModel = koinInject(),
                goBack = { navController.navigate(ViewRouter.HOME.value) }
            )
        }
    }
}

private enum class ViewRouter(val value: String) {
    HOME("home"),
    SETTINGS("setting"),
}