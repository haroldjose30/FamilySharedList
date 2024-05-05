//
//  NavigatorViewModelMock.swift
//  iosApp
//
//  Created by Harold José on 04/04/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

class NavigatorViewModelMock: NavigatorViewModelProtocol {
    var viewState: NavigatorViewState = .loading
    func checkIfNeedToCreateNewAccount() async {}
    func showError(e: Error) {}
}
