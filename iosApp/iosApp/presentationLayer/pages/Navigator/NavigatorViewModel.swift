//
//  NavigatorViewModel.swift
//  iosApp
//
//  Created by Harold José on 04/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

class NavigatorViewModel: NavigatorViewModelProtocol {
    private let getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase

    init(getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase) {
        self.getOrCreateAccountFromLocalUuidUseCase = getOrCreateAccountFromLocalUuidUseCase
    }

    //@MainActor
    func checkIfNeedToCreateNewAccount() async {
        //TODO: handle error
        _ = try? await getOrCreateAccountFromLocalUuidUseCase.execute()
    }
}

