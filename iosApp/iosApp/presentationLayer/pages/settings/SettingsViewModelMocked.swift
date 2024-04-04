import Foundation
import SwiftUI
import shared

class SettingsViewModelMocked: SettingsViewModelProtocol {
    @Published var myAccount: AccountModel? = nil
    @Published var accountShortCodeForShareTitle: String = "A34TG"
    @Published var accountsSharedWithMeTitle: String = "Acessar conta compartilhada"
    @Published var accountsSharedWithMeSubtitle: String = "Obs: atualmente limitada apenas a 1 conta"
    var goBack: () -> Void = {}

    func getAccount() {
        myAccount = AccountModel(uuid: "mockedUUid")
        accountShortCodeForShareTitle = "XY5A4"
        accountsSharedWithMeTitle = "Mocked Title"
        accountsSharedWithMeSubtitle = "DbAcc_74287346238746828i3"
    }

    func accessSharedAccountWithCode(code: String) {}

    func shareMyCode() {}

    func openAppHomePage() {}
}

