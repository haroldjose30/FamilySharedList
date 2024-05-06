import Foundation
import shared
import KMPNativeCoroutinesAsync

class FamilyListViewModel: FamilyListViewModelProtocol {

    @Published var viewState: FamilyListViewState = .initial
    @Published var familyListModels: [FamilyListModel] = []
    @Published var isShowingBarcodeBottomSheet: Bool = false
    @Published var newItemName: String = ""
    @Published var quantity: Int = 1
    @Published var tabIndex: FamilyListPageTabEnum = .pending {
        didSet {
            Task { await loadData(fromNetwork: false, showLoading: false) }
        }
    }
    var selectedItemUuid: String = ""

    var goToSetting: () -> Void = {}
    var goToQuickInsert: () -> Void = {}
    var goToBarcodeScanner: () -> Void = {}

    private var accountModel: AccountModel?

    private let getAllFamilyListUseCase: GetAllFamilyListUseCase
    private let createFamilyListUseCase: CreateFamilyListUseCase
    private let updateFamilyListUseCase: UpdateFamilyListUseCase
    private let deleteFamilyListUseCase: DeleteFamilyListUseCase
    private let getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
    private let getProductByCodeUseCase: GetProductByCodeUseCase
    private let crashlytics: IFirebaseCrashlytics

    init(
        getAllFamilyListUseCase: GetAllFamilyListUseCase,
        createFamilyListUseCase: CreateFamilyListUseCase,
        updateFamilyListUseCase: UpdateFamilyListUseCase,
        deleteFamilyListUseCase: DeleteFamilyListUseCase,
        getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
        getProductByCodeUseCase: GetProductByCodeUseCase,
        crashlytics: IFirebaseCrashlytics
    ) {
        self.getAllFamilyListUseCase = getAllFamilyListUseCase
        self.createFamilyListUseCase = createFamilyListUseCase
        self.updateFamilyListUseCase = updateFamilyListUseCase
        self.deleteFamilyListUseCase = deleteFamilyListUseCase
        self.getOrCreateAccountFromLocalUuidUseCase = getOrCreateAccountFromLocalUuidUseCase
        self.getProductByCodeUseCase = getProductByCodeUseCase
        self.crashlytics = crashlytics
    }

    @MainActor
    func loadData(fromNetwork: Bool, showLoading: Bool) async {
        if (showLoading) {
            viewState = .loading
        }

        do {
            accountModel = try await asyncFunction(for: getOrCreateAccountFromLocalUuidUseCase.execute())
        } catch {
            showError(e: error)
            return
        }


        if fromNetwork {
            do {
                let result = try await asyncFunction(for: getAllFamilyListUseCase.execute())
                familyListModels = result.sorted { $0.name.lowercased() < $1.name.lowercased() }
            } catch {
                showError(e: error)
                return
            }
        }

        if (showLoading) {
            viewState = .success
        }
    }

    @MainActor
    func add() async {
        if newItemName.isEmpty {
            return
        }

        if newItemName.isDigitsOnly() {
            let barcode = newItemName
            newItemName = ""
            await addBy(barcode: barcode)
            return
        }

        let item = FamilyListModel(
            name: newItemName,
            isCompleted: false, 
            isPrioritized: tabIndex.isPrioritized(), 
            quantity: quantity.toInt32()
        )
        newItemName = ""
        viewState = .loading
        do {
            _ = try await asyncFunction(for: createFamilyListUseCase.execute(item: item))
        } catch {
            showError(e: error)
            return
        }

        await loadData(fromNetwork: true, showLoading: true)
    }

    private var isAddByBusy = false
    @MainActor
    func addBy(barcode: String) async {
        if barcode.isEmpty || isAddByBusy {
            return
        }

        isAddByBusy = true
        viewState = .loading
        defer {
            isAddByBusy = false
        }


        do {
            guard let productModelFounded = try await asyncFunction(for: getProductByCodeUseCase.execute(code: barcode)) else {
                return
            }

            if let index = familyListModels.firstIndex(where: {
                $0.uuid == selectedItemUuid ||
                $0.product?.code.caseInsensitiveCompare(productModelFounded.code) == .orderedSame ||
                $0.name.caseInsensitiveCompare(productModelFounded.productName) == .orderedSame
            }) {
                if familyListModels[index].isCompleted {
                    familyListModels[index].isCompleted = false
                } else {
                    //TODO: fix view not updated when quantity is changed
                    familyListModels[index].quantity += 1
                }

                familyListModels[index].isPrioritized = tabIndex.isPrioritized()
                if (familyListModels[index].product == nil) {
                    familyListModels[index].name = productModelFounded.productName
                }
                familyListModels[index].product = productModelFounded
                await update(item: familyListModels[index])
                if tabIndex.isCompleted() {
                    tabIndex = .pending
                }
                await loadData(fromNetwork: true, showLoading: true)
                return
            }

            let item = FamilyListModel(
                name: productModelFounded.productName,
                isCompleted: false,
                isPrioritized: tabIndex.isPrioritized(),
                quantity: quantity.toInt32(),
                price: 0,
                product: productModelFounded
            )

            _ = try await asyncFunction(for: createFamilyListUseCase.execute(item: item))
            await loadData(fromNetwork: true, showLoading: true)

        } catch {
            showError(e: error)
            return
        }
    }

    @MainActor
    func remove(uuid: String) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            viewState = .loading
            do {
                _ = try await asyncFunction(for: deleteFamilyListUseCase.execute(uuid: uuid))
                familyListModels.remove(at: index)
                await loadData(fromNetwork: false, showLoading: false)
            } catch {
                showError(e: error)
                return
            }
            viewState = .success
        }
    }

    @MainActor
    func updateIsCompleted(uuid: String, isCompleted: Bool) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            familyListModels[index].isCompleted = isCompleted
            if isCompleted {
                familyListModels[index].isPrioritized = false
            }
            await update(item: familyListModels[index])
            await loadData(fromNetwork: false, showLoading: true)
        }
    }

    @MainActor
    func updateIsPrioritized(uuid: String, isPrioritized: Bool) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            familyListModels[index].isPrioritized = isPrioritized
            await update(item: familyListModels[index])
            await loadData(fromNetwork: false, showLoading: true)
        }
    }

    @MainActor
    func updateName(uuid: String, name: String) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            familyListModels[index].name = name
            await update(item: familyListModels[index])
        }
    }

    @MainActor
    func updateQuantity(uuid: String, quantity: Int) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            familyListModels[index].quantity = quantity.toInt32()
            await update(item: familyListModels[index])
        }
    }

    @MainActor
    func updatePrice(uuid: String, price: Double) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            familyListModels[index].price = price
            await update(item: familyListModels[index])
        }
    }

    func showError(e: Error) {
        crashlytics.record(error: e)
        viewState = .error(message: e.localizedDescription, retryAction: {
            Task {
                await self.loadData(fromNetwork: true, showLoading: true)
            }
        })
    }

    @MainActor
    private func update(item: FamilyListModel) async {
        do {
            _ = try await asyncFunction(for: updateFamilyListUseCase.execute(item: item))
        } catch {
            showError(e: error)
            return
        }
    }
}

