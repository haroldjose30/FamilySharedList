package dev.haroldjose.familysharedlist.presentationLayer.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

//reference:
//https://tomas-repcik.medium.com/making-extensible-settings-screen-in-jetpack-compose-from-scratch-2558170dd24d
@Composable
internal fun SettingsSharedPage(
    goBack: () -> Unit,
    viewModel: ISettingsSharedViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = "FamilyListPage"){
        viewModel.getAccount()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Configurações"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {

            SettingsItem(
                title = "Meu código de compartilhamento",
                subtitle = viewModel.accountShortCodeForShareTitle
            ) {
                coroutineScope.launch {
                    viewModel.shareMyCode()
                }
            }

            SettingsItemWithInputText(
                title = viewModel.accountsSharedWithMeTitle,
                subtitle = viewModel.accountsSharedWithMeSubtitle
            ) {
                coroutineScope.launch {
                    viewModel.accessSharedAccountWithCode(code = it)
                }
            }

            SettingsItem(
                title = "Sobre este app",
            ) {
                coroutineScope.launch {
                    viewModel.openAppHomePage()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsItem(
    title: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp,8.dp,16.dp,8.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = title
                )
            }
            Divider()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(16.dp,8.dp,16.dp,0.dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier
                            .padding(16.dp,0.dp,16.dp,8.dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = title
                )
            }
            Divider()
        }
    }
}

const val maxChar = 5
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsItemWithInputText(
    title: String,
    subtitle: String,
    onClick: (text:String) -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp,8.dp,16.dp,0.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(16.dp,0.dp,16.dp,8.dp),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                TextField(
                    value = textFieldState,
                    onValueChange = {
                        if (it.length <= maxChar) textFieldState = it
                    },
                    placeholder = { Text("ex: XY75K") },
                    singleLine = true,
                    modifier = Modifier
                        .padding(16.dp,0.dp,16.dp,8.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    onClick(textFieldState)
                },
            ) {
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = title
                )
            }
        }
        Divider()
    }
}