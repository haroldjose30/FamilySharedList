import Foundation

protocol NavigatorViewModelProtocol: ObservableObject {
    func checkIfNeedToCreateNewAccount() async
}
