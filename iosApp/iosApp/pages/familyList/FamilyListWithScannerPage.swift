//
//  FamilyListWithScannerPage.swift
//  iosApp
//
//  Created by Harold José on 20/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

@MainActor
struct FamilyListWithScannerPage: View {

    var viewModel: IFamilyListSharedViewModel
    var goToSetting: () -> Void

    @State private var isPresentingScanner = false

    var body: some View {
        FamilyListPage(
            viewModel: viewModel,
            goToSetting: goToSetting,
            goToScanner: goToScanner
        ).sheet(isPresented: $isPresentingScanner) {
            BarcodeScannerPage(
                onSuccess: { barcodeValue in
                    Task {
                        isPresentingScanner = false
                        try await viewModel.addBy(barcode: barcodeValue)
                    }
                }
            )
        }
    }

    func goToScanner() {
        isPresentingScanner = true
    }
}

#Preview {
    FamilyListWithScannerPage(
        viewModel: FamilyListSharedViewModelMocked(),
        goToSetting: {
            print("goToSetting tapped")
        }
    )
}
