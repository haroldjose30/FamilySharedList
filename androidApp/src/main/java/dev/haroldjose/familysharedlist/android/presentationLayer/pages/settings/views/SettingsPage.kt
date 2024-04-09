package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithInputTextView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleAndSubtitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsTopAppBarView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.ISettingsViewModel
import kotlinx.coroutines.launch

//reference:
//https://tomas-repcik.medium.com/making-extensible-settings-screen-in-jetpack-compose-from-scratch-2558170dd24d
@Composable
internal fun SettingsPage(
    viewModel: ISettingsViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "SettingsPage"){
        viewModel.getAccount()
    }

    Scaffold(
        topBar = SettingsTopAppBarView(viewModel)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {

            SettingsItemWithTitleAndSubtitleView(
                title = "Meu código de compartilhamento",
                subtitle = viewModel.accountShortCodeForShareTitle
            ) {
                coroutineScope.launch {
                    viewModel.shareMyCode()
                }
            }

            SettingsItemWithInputTextView(
                title = viewModel.accountsSharedWithMeTitle,
                subtitle = viewModel.accountsSharedWithMeSubtitle
            ) {
                coroutineScope.launch {
                    viewModel.accessSharedAccountWithCode(code = it)
                }
            }

            SettingsItemWithTitleView(
                title = "Sobre este app",
            ) {
                coroutineScope.launch {
                    viewModel.openAppHomePage()
                }
            }
        }
    }
}

