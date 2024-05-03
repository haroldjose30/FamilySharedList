import Foundation
import SwiftUI
import shared

class SettingsViewModelMocked: SettingsViewModelProtocol {
//    @Published var viewState: SettingsViewState = .Initial(
//        accountShortCodeForShareTitle: "A34TG",
//        accountsSharedWithMeTitle: "Acessar conta compartilhada",
//        accountsSharedWithMeSubtitle: "Obs: atualmente limitada apenas a 1 conta"
//    )

    //@Published var viewState: SettingsViewState = .loading
    @Published var viewState: SettingsViewState = .Error(message: "Error", retryAction: {})

    @Published var myAccount: AccountModel? = nil

    var goBack: () -> Void = {}

    func getAccount() {
        myAccount = AccountModel(uuid: "mockedUUid")
    }

    func accessSharedAccountWithCode(code: String) {}

    func shareMyCode() {}

    func openAppHomePage() {}
}

