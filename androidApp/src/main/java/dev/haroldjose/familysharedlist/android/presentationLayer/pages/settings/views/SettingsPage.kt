package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithInputTextView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsItemWithTitleAndSubtitleView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.views.components.SettingsTopAppBarView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModelMocked
import kotlinx.coroutines.launch

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.res.ResourcesCompat

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
                .fillMaxSize()
        ) {

            SettingsItemWithTitleAndSubtitleView(
                title = "Compartilhar minha conta",
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

            FamilyListLogo(viewModel)
        }
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



