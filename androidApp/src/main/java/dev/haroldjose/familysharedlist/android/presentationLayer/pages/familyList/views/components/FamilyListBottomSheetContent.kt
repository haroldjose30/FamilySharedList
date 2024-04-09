package dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.android.presentationLayer.components.QrCodeScannerIconView
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.barcodeScanner.QrScannerScreen
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FamilyListBottomSheetContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: IFamilyListViewModel
): @Composable() (ColumnScope.() -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    return {
        Box(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween
                ) {
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                    bottomSheetScaffoldState.bottomSheetState.partialExpand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    ) {
                        Icon(
                            QrCodeScannerIconView(),
                            contentDescription = "Scanner"
                        )
                    }
                    OutlinedTextField(
                        value = viewModel.newItemName,
                        onValueChange = { viewModel.newItemName = it },
                        placeholder = { Text("Informe o novo item") },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    )
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.add()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "Add"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                Logger.d("FamilyListSharedPage", "Scanner open")
                QrScannerScreen(
                    modifier = Modifier.fillMaxWidth(),
                    onQrCodeScanned = { barcodeScanned ->
                        Logger.d("FamilyListSharedPage", "barcodeScanned $barcodeScanned")
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.partialExpand()
                            viewModel.addBy(barcode = barcodeScanned)
                        }
                    }
                )
            }
        }
    }
}