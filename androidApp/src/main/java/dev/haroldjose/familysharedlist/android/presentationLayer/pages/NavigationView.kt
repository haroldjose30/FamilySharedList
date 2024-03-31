package dev.haroldjose.familysharedlist.android.presentationLayer.pages

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.SettingsPage
import org.koin.compose.koinInject

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(ViewRouter.HOME.value) {
            FamilyListPage(
                viewModel = koinInject(),
                goToSetting = { navController.navigate(ViewRouter.SETTINGS.value)}
            )
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