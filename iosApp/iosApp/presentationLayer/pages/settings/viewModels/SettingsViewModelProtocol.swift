import Foundation
import shared

protocol SettingsViewModelProtocol: ObservableObject {
    var myAccount: AccountModel? { get set }
    var accountShortCodeForShareTitle: String { get }
    var accountsSharedWithMeTitle: String { get }
    var accountsSharedWithMeSubtitle: String { get }
    var isLoading: Bool { get }

    var goBack: () -> Void { get set }

    func getAccount() async
    func accessSharedAccountWithCode(code: String) async
    func shareMyCode()
    func openAppHomePage()
}
