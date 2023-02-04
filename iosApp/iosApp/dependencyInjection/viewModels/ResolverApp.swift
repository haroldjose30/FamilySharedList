import shared

protocol ResolverAppProtocol: ResolverProtocol {
    
    @MainActor func resolve() -> FamilyListViewModel
}

class ResolverApp: ResolverAppProtocol {}

extension ResolverAppProtocol {
    
    @MainActor
    func resolve() -> FamilyListViewModel {
        
        FamilyListViewModel(
            createFamilyListUseCase: KoinApplication.shared.inject(),
            getAllFamilyListUseCase: KoinApplication.shared.inject(),
            updateFamilyListUseCase: KoinApplication.shared.inject(),
            deleteFamilyListUseCase: KoinApplication.shared.inject()
        )
    }
}
