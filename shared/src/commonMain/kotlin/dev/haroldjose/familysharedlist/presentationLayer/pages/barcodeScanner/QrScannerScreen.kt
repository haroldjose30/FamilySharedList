package dev.haroldjose.familysharedlist.presentationLayer.pages.barcodeScanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun QrScannerScreen(modifier: Modifier, onQrCodeScanned: (String) -> Unit)