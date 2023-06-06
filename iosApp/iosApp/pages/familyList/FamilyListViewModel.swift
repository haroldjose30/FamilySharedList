import Combine
import shared
import KMPNativeCoroutinesAsync

@MainActor
class FamilyListViewModel: ObservableObject {
    
    @Published var familyListModels = [FamilyListModel]()
    @Published var loading = false
    @Published var newItemName = ""
    
   private let createFamilyListUseCase: CreateFamilyListUseCase
   private let getAllFamilyListUseCase: GetAllFamilyListUseCase
   private let updateFamilyListUseCase: UpdateFamilyListUseCase
   private let deleteFamilyListUseCase: DeleteFamilyListUseCase
    
    init(
        createFamilyListUseCase: CreateFamilyListUseCase,
        getAllFamilyListUseCase: GetAllFamilyListUseCase,
        updateFamilyListUseCase: UpdateFamilyListUseCase,
        deleteFamilyListUseCase: DeleteFamilyListUseCase
    ) {
        
        self.createFamilyListUseCase = createFamilyListUseCase
        self.getAllFamilyListUseCase = getAllFamilyListUseCase
        self.updateFamilyListUseCase = updateFamilyListUseCase
        self.deleteFamilyListUseCase = deleteFamilyListUseCase
    }
    
    func loadData() async {
        
        loading = true
        let result = await asyncResult(for: getAllFamilyListUseCase.execute())
        switch result {
        case .success(let data):
            familyListModels = data
            loading = false
        case .failure(let error):
            showError(error)
            loading = false
        }
    }
    
    func add() async {
        
        guard !newItemName.isEmpty else {
            return
        }

        let item = FamilyListModel(
            name: newItemName
        )
        newItemName = ""
        loading = true
        let result = await asyncResult(for: createFamilyListUseCase.execute(item: item))
        switch result {
        case .success:
            await loadData()
        case .failure(let error):
            showError(error)
            loading = false
        }
    }
    
    func showError(_ error: Error) {
        
        print(error)
    }
    
    func update(item: FamilyListModel) async {
        
        //Todo: implement debounce
        loading = true
        let result = await asyncResult(for: updateFamilyListUseCase.execute(item: item))
        switch result {
        case .success:
            loading = false
        case .failure(let error):
            showError(error)
            loading = false
        }
    }
    
    func remove(item: FamilyListModel) async {

        loading = true
        let result = await asyncResult(for: deleteFamilyListUseCase.execute(uuid: item.uuid))
        switch result {
        case .success:
            await loadData()
        case .failure(let error):
            showError(error)
            loading = false
        }
    }
    
    func onDelete(at offsets: IndexSet) {
        
        if let index = offsets.first {
            let item = self.familyListModels.remove(at: index)
            Task {
                await remove(item: item)
            }

        }
    }
}
