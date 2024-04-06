import Foundation
import shared

class FamilyListViewModelMocked: FamilyListViewModelProtocol {
    var familyListModelsFiltered: [FamilyListModel] = [
        FamilyListModel(name: "Mock Item 01", isCompleted: false, quantity: 1),
        FamilyListModel(name: "Mock Item 02", isCompleted: true, quantity: 2),
        FamilyListModel(name: "Mock Item 03", isCompleted: false, quantity: 3)
    ]

    var loading: Bool = false
    var newItemName: String = "newItem"
    var quantity: Int = 1
    var tabIndex: FamilyListPageTabEnum = .prioritized

    var goToSetting: () -> Void = {}
    var goToQuickInsert: () -> Void = {}

    func loadData(tabIndex: FamilyListPageTabEnum, fromNetwork: Bool) {}
    func add() {}
    func addBy(barcode: String) {}
    func showError(e: Error) {}
    func remove(uuid: String) {}
    func updateIsCompleted(uuid: String, isCompleted: Bool) {}
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) {}
    func updateName(uuid: String, name: String) {}
    func updateQuantity(uuid: String, quantity: Int) {}
}

