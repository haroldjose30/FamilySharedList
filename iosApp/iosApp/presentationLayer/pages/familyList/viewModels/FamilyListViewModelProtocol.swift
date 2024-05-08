import Foundation
import shared

protocol FamilyListViewModelProtocol: ObservableObject {
    var viewState: FamilyListViewState { get set }
    var familyListModels: [FamilyListModel] { get set }
    var familyListModelsGrouped: [FamilyListModelsGrouped] { get set }
    
    var isShowingBarcodeBottomSheet: Bool { get set }
    var isShowingOpenImageBottomSheet: Bool { get set }

    var newItemName: String { get set }
    var selectedItemUuid: String { get set }
    var tabIndex: FamilyListPageTabEnum { get set }
    var openImageSelectedItem: FamilyListModel? { get set }

    var goToSetting: () -> Void { get set }
    var goToQuickInsert: () -> Void { get set }

    var sumOfPrioritized: Double { get set }
    var sumOfPending: Double { get set }
    var sumOfCompleted: Double { get set }

    func loadData(fromNetwork: Bool, showLoading: Bool) async
    func add() async
    func addBy(barcode: String) async
    func remove(uuid: String) async
    func updateIsCompleted(uuid: String, isCompleted: Bool) async
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) async
    func updateName(uuid: String, name: String) async
    func updateQuantity(uuid: String, quantity: Int) async
    func updatePrice(uuid: String, price: Double) async
    func openImage(item: FamilyListModel)
}

struct FamilyListModelsGrouped: Identifiable {
    let id: Date
    let items: [FamilyListModel]
    var priceTotal: Double {
        items.reduce(0.0) { partialResult, item in
            return partialResult + (Double(item.quantity) * item.price)
        }
    }
}
