import Foundation
class QuickInsertListViewModelMocked: QuickInsertListViewModelProtocol {
    var viewState: QuickInsertListViewState = .initial
    var textFieldTitle: String = "Informe os itens sendo cada linha um novo item."
    var loading: Bool = false
    var text: String = ""
    var goToFamilyListPage: () -> Void = {}

    func quickInsertItem() async {}
    func showError(e: Error) {}
}
