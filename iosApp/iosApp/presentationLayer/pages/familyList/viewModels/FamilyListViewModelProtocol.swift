import Foundation
import shared

protocol FamilyListViewModelProtocol: ObservableObject {
    var familyListModelsFiltered: [FamilyListModel] { get set }
    var loading: Bool { get set }
    var newItemName: String { get set }
    var quantity: Int { get set }
    var tabIndex: FamilyListPageTabEnum { get set }

    var goToSetting: () -> Void { get set }
    var goToQuickInsert: () -> Void { get set }

    func loadData(tabIndex: FamilyListPageTabEnum, fromNetwork: Bool) async
    func add() async
    func addBy(barcode: String) async
    func remove(uuid: String) async
    func updateIsCompleted(uuid: String, isCompleted: Bool) async
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) async
    func updateName(uuid: String, name: String) async
    func updateQuantity(uuid: String, quantity: Int) async
}
