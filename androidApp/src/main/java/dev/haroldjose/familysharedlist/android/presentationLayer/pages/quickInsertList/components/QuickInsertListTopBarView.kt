package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.IQuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.QuickInsertListPage
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.QuickInsertListViewModelMocked
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QuickInsertListTopBarView(
    viewModel: IQuickInsertListViewModel
) = @Composable {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Inclusão rápida") },
        navigationIcon = {
            IconButton(onClick = { viewModel.goToFamilyListPage() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,"Incluir")
            }
        },
        actions = {
            IconButton(onClick = { coroutineScope.launch { viewModel.quickInsertItem() } }) {
                Icon(Icons.Rounded.AddCircle,"Incluir")
            }
        },
    )
}

@Preview
@Composable
fun QuickInsertListTopBarView_Preview() {
    MyApplicationTheme {
        QuickInsertListPage( viewModel = QuickInsertListViewModelMocked())
    }
}
