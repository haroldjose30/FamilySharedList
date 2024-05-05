import Foundation

protocol NavigatorViewModelProtocol: ObservableObject {
    var viewState: NavigatorViewState { get set }
    func checkIfNeedToCreateNewAccount() async
    func showError(e: Error)
}
