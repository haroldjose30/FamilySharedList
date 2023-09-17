//
//  SettingsPage.swift
//  iosApp
//
//  Created by Harold José on 28/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI
import shared

struct SettingsPage: UIViewControllerRepresentable {

    private let resolverApp = ResolverApp()
    var goBack: () -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        SettingsPage_iosKt.SettingsPageViewController(
            viewModel: resolverApp.resolve(),
            goBack: goBack
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
