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
    func makeUIViewController(context: Context) -> UIViewController {
        SettingsSharedPage_iosKt.SettingsPageViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
