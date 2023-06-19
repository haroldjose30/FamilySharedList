import shared

protocol ResolverPreviewProtocol: ResolverProtocol {
    
    @MainActor func resolve() -> FamilyListViewModel
}

class ResolverPreview: ResolverPreviewProtocol {}

extension ResolverPreviewProtocol {
    
    @MainActor
    func resolve() -> FamilyListViewModel {
        GlobalState.companion.isRunningUITests = true
        
        return FamilyListViewModel(
            createFamilyListUseCase: KoinApplication.shared.inject(),
            getAllFamilyListUseCase: KoinApplication.shared.inject(),
            updateFamilyListUseCase: KoinApplication.shared.inject(),
            deleteFamilyListUseCase: KoinApplication.shared.inject(),
            getOrCreateAccountFromLocalUuidUseCase: KoinApplication.shared.inject()
        )
    }
}
