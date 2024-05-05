import Foundation

protocol QuickInsertListViewModelProtocol: ObservableObject {
    var viewState: QuickInsertListViewState { get set }
    var text: String { get set }
    var textFieldTitle: String { get set }
    var goToFamilyListPage: () -> Void { get set }
    func quickInsertItem() async
    func showError(e: Error)
}

