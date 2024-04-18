package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.ErrorPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.NavigatorViewState
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModelMocked
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewState
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithInputTextView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleAndSubtitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsTopAppBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

        when (viewModel.viewState) {
            is SettingsViewState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.requiredSize(100.dp),
                    color = Color.LightGray
                )
            }
            is SettingsViewState.Error -> {
                val errorData = (viewModel.viewState as SettingsViewState.Error)
                ErrorPage(
                    errorData.message,
                    errorData.retryAction
                )
            }
            else -> {
                SettingsView(
                    paddingValues = it,
                    viewModel = viewModel,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

@Composable
private fun SettingsView(
    paddingValues: PaddingValues,
    viewModel: ISettingsViewModel,
    coroutineScope: CoroutineScope
) {
    val accountShortCodeForShareTitle: String = when (viewModel.viewState) {
        is SettingsViewState.Initial -> (viewModel.viewState as SettingsViewState.Initial).accountShortCodeForShareTitle
        is SettingsViewState.Success -> (viewModel.viewState as SettingsViewState.Success).accountShortCodeForShareTitle
        else -> ""
    }

    val accountsSharedWithMeTitle: String = when (viewModel.viewState) {
        is SettingsViewState.Initial -> (viewModel.viewState as SettingsViewState.Initial).accountsSharedWithMeTitle
        is SettingsViewState.Success -> (viewModel.viewState as SettingsViewState.Success).accountsSharedWithMeTitle
        else -> ""
    }

    val accountsSharedWithMeSubtitle: String = when (viewModel.viewState) {
        is SettingsViewState.Initial -> (viewModel.viewState as SettingsViewState.Initial).accountsSharedWithMeSubtitle
        is SettingsViewState.Success -> (viewModel.viewState as SettingsViewState.Success).accountsSharedWithMeSubtitle
        else -> ""
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
    ) {

        SettingsItemWithTitleAndSubtitleView(
            title = "Compartilhar minha conta",
            subtitle = accountShortCodeForShareTitle
        ) {
            coroutineScope.launch {
                viewModel.shareMyCode()
            }
        }

        SettingsItemWithInputTextView(
            title = accountsSharedWithMeTitle,
            subtitle = accountsSharedWithMeSubtitle
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

        FamilyListLogo(viewModel)
    }
}

@Composable
private fun FamilyListLogo(
    viewModel: ISettingsViewModel
) {
    ResourcesCompat.getDrawable(
        LocalContext.current.resources,
        R.mipmap.ic_launcher, LocalContext.current.theme
    )?.let { drawable ->
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                // painter = painterResource(R.mipmap.ic_launcher),
                bitmap = bitmap.asImageBitmap(),
                "Family List Logo",
                modifier = Modifier
                    .requiredSize(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = "Family List",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp,16.dp,16.dp,8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Lista de compras compartilhada com a sua familia",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(16.dp,16.dp,16.dp,8.dp),
                textAlign = TextAlign.Center
            )

            Text(text = viewModel.getVersion())
        }
    }
}


@Preview
@Composable
fun SettingsPage_Preview() {
    MyApplicationTheme {
        SettingsPage(viewModel = SettingsViewModelMocked())
    }
}



