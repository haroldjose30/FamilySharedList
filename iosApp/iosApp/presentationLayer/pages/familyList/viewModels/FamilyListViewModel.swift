import Foundation
import shared

class FamilyListViewModel: FamilyListViewModelProtocol {
    
    @Published var familyListModels: [FamilyListModel] = []
    @Published var isLoading: Bool = false
    @Published var newItemName: String = ""
    @Published var quantity: Int = 1
    @Published var tabIndex: FamilyListPageTabEnum = .pending {
        didSet {
            Task { await loadData(fromNetwork: false, showLoading: false) }
        }
    }

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

    init(
        getAllFamilyListUseCase: GetAllFamilyListUseCase,
        createFamilyListUseCase: CreateFamilyListUseCase,
        updateFamilyListUseCase: UpdateFamilyListUseCase,
        deleteFamilyListUseCase: DeleteFamilyListUseCase,
        getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
        getProductByCodeUseCase: GetProductByCodeUseCase
    ) {
        self.getAllFamilyListUseCase = getAllFamilyListUseCase
        self.createFamilyListUseCase = createFamilyListUseCase
        self.updateFamilyListUseCase = updateFamilyListUseCase
        self.deleteFamilyListUseCase = deleteFamilyListUseCase
        self.getOrCreateAccountFromLocalUuidUseCase = getOrCreateAccountFromLocalUuidUseCase
        self.getProductByCodeUseCase = getProductByCodeUseCase
    }

    @MainActor
    func loadData(fromNetwork: Bool, showLoading: Bool) async {
        if (showLoading) { isLoading = true }

        //TODO: handle error
        accountModel = try? await getOrCreateAccountFromLocalUuidUseCase.execute()

        if fromNetwork {
            //TODO: handle error
            if let result = try? await getAllFamilyListUseCase.execute() {
                familyListModels = result.sorted { $0.name.lowercased() < $1.name.lowercased() }
            }
        }

        if (showLoading) { isLoading = false }
    }

    @MainActor
    func add() async {
        if newItemName.isEmpty {
            return
        }

        if newItemName.lowercased().hasPrefix("debug") {
            newItemName = newItemName.replacingOccurrences(of: "debug", with: "", options: .caseInsensitive)
            await addBy(barcode: newItemName)
            newItemName = ""
            return
        }

        let item = FamilyListModel(
            name: newItemName,
            isCompleted: false, 
            isPrioritized: tabIndex.isPrioritized(), 
            quantity: quantity.toInt32()
        )
        newItemName = ""
        isLoading = true
        //TODO: handle error
        try? await createFamilyListUseCase.execute(item: item)
        await loadData(fromNetwork: true, showLoading: true)
    }

    @MainActor
    func addBy(barcode: String) async {
        if barcode.isEmpty {
            return
        }

        isLoading = true
        //TODO: handle error
        guard let productModelFounded = try? await getProductByCodeUseCase.execute(code: barcode) else {
            isLoading = false
            return
        }

        if let itemFounded = familyListModels.first(where: { $0.name.caseInsensitiveCompare(productModelFounded.productName) == .orderedSame }) {
            if itemFounded.isCompleted {
                itemFounded.isCompleted = false
                itemFounded.isPrioritized = tabIndex.isPrioritized()
                await update(item: itemFounded)
                if tabIndex == .completed {
                    tabIndex = .pending
                }
                await loadData(fromNetwork: true, showLoading: true)
            } else {
                itemFounded.isPrioritized = tabIndex.isPrioritized()
                itemFounded.quantity += 1
                await update(item: itemFounded)
                await loadData(fromNetwork: true, showLoading: true)
            }
        } else {
            let item = FamilyListModel(
                name: productModelFounded.productName,
                isCompleted: false, 
                isPrioritized: tabIndex.isPrioritized(),
                quantity: quantity.toInt32()
            )
            //TODO: handle error
            try? await createFamilyListUseCase.execute(item: item)
            await loadData(fromNetwork: true, showLoading: true)
        }
    }

    @MainActor
    func remove(uuid: String) async {
        if let index = familyListModels.firstIndex(where: { $0.uuid == uuid }) {
            isLoading = true
            //TODO: handle error
            try? await deleteFamilyListUseCase.execute(uuid: uuid)
            familyListModels.remove(at: index)
            await loadData(fromNetwork: false, showLoading: false)
            isLoading = false
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

    func showError(e: Error) {
        //TODO: Handle error
    }

    @MainActor
    private func update(item: FamilyListModel) async {
        try? await updateFamilyListUseCase.execute(item: item)
    }
}

