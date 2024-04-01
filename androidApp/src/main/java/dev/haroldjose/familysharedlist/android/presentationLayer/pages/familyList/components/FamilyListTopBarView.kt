package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FamilyListTopBarView(
    viewModel: IFamilyListViewModel
) = @Composable {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Lista de Compras")
        },
        actions = {
            IconButton(onClick = { viewModel.goToSetting() }) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Configurações"
                )
            }
        },
    )
}