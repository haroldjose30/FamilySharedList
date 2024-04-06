import Foundation
import shared

class FamilyListViewModelMocked: FamilyListViewModelProtocol {
    var familyListModels: [FamilyListModel] = [
        FamilyListModel(uuid: "", name: "Item 01", isCompleted: false, isPrioritized: false, quantity: 1),
        FamilyListModel(uuid: "", name: "Item 02", isCompleted: false, isPrioritized: false, quantity: 2),
        FamilyListModel(uuid: "", name: "Item 03", isCompleted: false, isPrioritized: true, quantity: 3),
        FamilyListModel(uuid: "", name: "Item 04", isCompleted: false, isPrioritized: true, quantity: 4),
        FamilyListModel(uuid: "", name: "Item 05", isCompleted: false, isPrioritized: true, quantity: 5),
        FamilyListModel(uuid: "", name: "Item 06", isCompleted: true, isPrioritized: true, quantity: 6),
        FamilyListModel(uuid: "", name: "Item 07", isCompleted: false, isPrioritized: true, quantity: 7),
        FamilyListModel(uuid: "", name: "Item 08", isCompleted: false, isPrioritized: true, quantity: 8),
        FamilyListModel(uuid: "", name: "Item 09", isCompleted: true, isPrioritized: false, quantity: 9),
        FamilyListModel(uuid: "", name: "Item 10", isCompleted: false, isPrioritized: true, quantity: 10),
        FamilyListModel(uuid: "", name: "Item 11", isCompleted: true, isPrioritized: true, quantity: 11),
        FamilyListModel(uuid: "", name: "Item 12", isCompleted: false, isPrioritized: false, quantity: 12),
    ]

    var loading: Bool = false
    var newItemName: String = "newItem"
    var quantity: Int = 1
    var tabIndex: FamilyListPageTabEnum = .prioritized

    var goToSetting: () -> Void = {}
    var goToQuickInsert: () -> Void = {}

    func loadData(fromNetwork: Bool) {}
    func add() {}
    func addBy(barcode: String) {}
    func showError(e: Error) {}
    func remove(uuid: String) {}
    func updateIsCompleted(uuid: String, isCompleted: Bool) {}
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) {}
    func updateName(uuid: String, name: String) {}
    func updateQuantity(uuid: String, quantity: Int) {}
}

