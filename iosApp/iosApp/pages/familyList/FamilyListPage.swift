//
//  FamilyListPage.swift
//  iosApp
//
//  Created by Harold José on 17/09/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI
import shared

struct FamilyListPage: UIViewControllerRepresentable {

    var viewModel: IFamilyListSharedViewModel
    var goToSetting: () -> Void
    var goToScanner: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        FamilyListPage_iosKt.FamilyListPageViewController(
            viewModel: viewModel,
            goToSetting: goToSetting, 
            goToScanner: goToScanner
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

#Preview {
    FamilyListPage(
        viewModel: FamilyListSharedViewModelMocked(),
        goToSetting: {},
        goToScanner: {}
    )
}
