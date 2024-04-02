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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.android.R
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.presentationLayer.pages.familyList.FamilyListViewModelMocked

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
            IconButton(onClick = { viewModel.goToQuickInsert() }) {
                Icon(painter = painterResource(R.drawable.list_alt_add),"Configurações")
            }
            IconButton(onClick = { viewModel.goToSetting() }) {
                Icon(Icons.Rounded.Settings,"Configurações")
            }
        },
    )
}

@Preview
@Composable
fun FamilyListTopBarView_Preview() {
    MyApplicationTheme {
        FamilyListPage( viewModel = FamilyListViewModelMocked())
    }
}