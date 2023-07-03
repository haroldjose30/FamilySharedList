package dev.haroldjose.familysharedlist.android.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.android.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.presentationLayer.pages.settings.SettingsPage

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(Destination.HOME.value) {
            FamilyListPage(
                goToSetting = { navController.navigate(Destination.SETTINGS.value)}
            )
        }
        composable(Destination.SETTINGS.value) {
            SettingsPage()
        }
    }
}

private enum class Destination(val value: String) {
    HOME("home"),
    SETTINGS("setting"),
}