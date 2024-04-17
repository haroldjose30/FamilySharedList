import Foundation
import shared

class FamilyListViewModelMocked: FamilyListViewModelProtocol {
    var familyListModels: [FamilyListModel] = [
        FamilyListModel(uuid: "", name: "Item 01", isCompleted: false, isCompletedDate: nil, isPrioritized: false, quantity: 1, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 02", isCompleted: false, isCompletedDate: nil, isPrioritized: false, quantity: 2, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 03", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 3, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 04", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 4, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 05", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 5, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 06", isCompleted: true, isCompletedDate: nil, isPrioritized: true, quantity: 6, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 07", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 7, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 08", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 8, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 09", isCompleted: true, isCompletedDate: nil, isPrioritized: false, quantity: 9, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 10", isCompleted: false, isCompletedDate: nil, isPrioritized: true, quantity: 10, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 11", isCompleted: true, isCompletedDate: nil, isPrioritized: true, quantity: 11, price: 0, product: nil),
        FamilyListModel(uuid: "", name: "Item 12", isCompleted: false, isCompletedDate: nil, isPrioritized: false, quantity: 12, price: 0, product: nil),
    ]

    var isLoading: Bool = false
    var isShowingBarcodeBottomSheet: Bool = false
    var newItemName: String = ""
    var quantity: Int = 1
    var tabIndex: FamilyListPageTabEnum = .prioritized

    var goToSetting: () -> Void = {}
    var goToQuickInsert: () -> Void = {}
    var goToBarcodeScanner: () -> Void = {}

    func loadData(fromNetwork: Bool, showLoading: Bool) {}
    func add() {}
    func addBy(barcode: String) {}
    func showError(e: Error) {}
    func remove(uuid: String) {}
    func updateIsCompleted(uuid: String, isCompleted: Bool) {}
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) {}
    func updateName(uuid: String, name: String) {}
    func updateQuantity(uuid: String, quantity: Int) {}
}

