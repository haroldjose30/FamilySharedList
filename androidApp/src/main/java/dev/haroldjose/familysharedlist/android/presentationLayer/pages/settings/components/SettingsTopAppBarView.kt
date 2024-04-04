package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.components

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.ISettingsViewModel

@SuppressLint("ComposableNaming")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsTopAppBarView(
    viewModel: ISettingsViewModel
) = @Composable {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Configurações"
            )
        },
        navigationIcon = {
            IconButton(onClick = { viewModel.goBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}