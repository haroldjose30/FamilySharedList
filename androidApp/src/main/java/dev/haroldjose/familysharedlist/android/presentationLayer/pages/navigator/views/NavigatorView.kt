package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.ErrorPage
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigatorView(
    navigatorViewModel: INavigatorViewModel = koinViewModel<NavigatorViewModel>(),
    quickInsertListViewModel: IQuickInsertListViewModel = koinViewModel<QuickInsertListViewModel>(),
    settingsViewModel: ISettingsViewModel = koinViewModel<SettingsViewModel>(),
    familyListViewModel: IFamilyListViewModel = koinViewModel<FamilyListViewModel>()
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()

    quickInsertListViewModel.goToFamilyListPage = { navController.navigate(NavigatorViewRouter.FAMILY_LIST.value) }
    settingsViewModel.goBack = { navController.navigate(NavigatorViewRouter.FAMILY_LIST.value) }
    familyListViewModel.goToSetting = { navController.navigate(NavigatorViewRouter.SETTINGS.value) }
    familyListViewModel.goToQuickInsert = { navController.navigate(NavigatorViewRouter.QUICK_INSERT.value) }

    LaunchedEffect(key1 = "NavigatorView") {
        navigatorViewModel.checkIfNeedToCreateNewAccount(navController)
    }

    NavHost(navController = navController, startDestination = NavigatorViewRouter.NAVIGATOR.value) {
        composable(NavigatorViewRouter.FAMILY_LIST.value) {
            FamilyListPage(familyListViewModel)
        }
        composable(NavigatorViewRouter.SETTINGS.value) {
            SettingsPage(settingsViewModel)
        }
        composable(NavigatorViewRouter.QUICK_INSERT.value) {
            QuickInsertListPage(quickInsertListViewModel)
        }
        composable(NavigatorViewRouter.NAVIGATOR.value) {
            LoadingOrErrorPage(navController, coroutineScope, navigatorViewModel)
        }
    }
}

@Composable
fun LoadingOrErrorPage(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    navigatorViewModel: INavigatorViewModel
) {
    when (navigatorViewModel.viewState) {
        is NavigatorViewState.none, NavigatorViewState.Loading, NavigatorViewState.Success -> CircularProgressIndicator(
            modifier = Modifier
                .requiredSize(100.dp),
            color = Color.LightGray
        )
        is NavigatorViewState.Error -> ErrorPage((navigatorViewModel.viewState as NavigatorViewState.Error).message) {
            coroutineScope.launch {
                navigatorViewModel.checkIfNeedToCreateNewAccount(
                    navController
                )
            }
        }
    }
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


