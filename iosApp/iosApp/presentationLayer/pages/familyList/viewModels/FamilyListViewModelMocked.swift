import Foundation
import shared

class FamilyListViewModelMocked: FamilyListViewModelProtocol {
    var viewState: FamilyListViewState = .success

    var familyListModels: [FamilyListModel] = Samples.FamilyList.companion.list1

    var isShowingBarcodeBottomSheet: Bool = false
    var newItemName: String = ""
    var quantity: Int = 1
    var tabIndex: FamilyListPageTabEnum = .pending
    var selectedItemUuid: String = ""

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
    func updatePrice(uuid: String, price: Double) {}
}

