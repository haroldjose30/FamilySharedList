import Foundation
import shared

protocol SettingsViewModelProtocol: ObservableObject {
    var viewState: SettingsViewState { get set }
    var myAccount: AccountModel? { get set }

    var goBack: () -> Void { get set }

    func getAccount() async
    func accessSharedAccountWithCode(code: String) async
    func shareMyCode()
    func openAppHomePage()
}
