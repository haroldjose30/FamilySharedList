import Foundation

protocol QuickInsertListViewModelProtocol: ObservableObject {
    var loading: Bool { get set }
    var text: String { get set }
    var textFieldTitle: String { get set }
    var goToFamilyListPage: () -> Void { get set }
    func quickInsertItem() async
}

