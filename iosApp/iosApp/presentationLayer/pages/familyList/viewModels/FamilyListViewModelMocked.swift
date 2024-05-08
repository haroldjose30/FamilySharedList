import Foundation
import shared

class FamilyListViewModelMocked: FamilyListViewModelProtocol {
    var viewState: FamilyListViewState = .success

    var familyListModels: [FamilyListModel] = Samples.FamilyList.companion.list1
    var familyListModelsGrouped: [FamilyListModelsGrouped] = {
        Dictionary(grouping: Samples.FamilyList.companion.list1) { item in
            item.isCompletedDate?.toDateNoTime() ?? GlobalStateKt.defaultLocalDateTime.toDateNoTime()
        }.map { key, items in
            FamilyListModelsGrouped(id: key, items: items)
        }.sorted { $0.id > $1.id }
    }()

    var isShowingBarcodeBottomSheet: Bool = false
    var isShowingOpenImageBottomSheet: Bool = false

    var newItemName: String = ""
    var tabIndex: FamilyListPageTabEnum = .pending
    var selectedItemUuid: String = ""
    var openImageSelectedItem: FamilyListModel? = nil

    var sumOfPrioritized: Double = 1.11
    var sumOfPending: Double = 2.22
    var sumOfCompleted: Double = 3.33

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
    func openImage(item: FamilyListModel) {}
}
