package dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.android.app.MyApplicationTheme
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.components.QuickInsertListTopBarView

@Composable
fun QuickInsertListPage(
    viewModel: IQuickInsertListViewModel
) {
    val labelText = remember { mutableStateOf("Informe os itens sendo cada linha um novo item.") }
    Scaffold(
        topBar = QuickInsertListTopBarView(viewModel)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {

            if (viewModel.loading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            OutlinedTextField(
                value = viewModel.text,
                onValueChange = {
                    viewModel.text = it
                    val count = it.lines().count()
                    val itemText = if (count==1) "item" else "itens"
                    labelText.value = "$count $itemText"
                },
                modifier = Modifier.fillMaxSize(),
                label = { Text(labelText.value) },
                placeholder = { Text("Ex: 1 refrigerante \nnutela 5\n") },
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text
                ),
            )
        }
    }
}

@Preview
@Composable
fun QuickInsertListPage_Preview() {
    MyApplicationTheme {
        QuickInsertListPage( viewModel = QuickInsertListViewModelMocked())
    }
}

