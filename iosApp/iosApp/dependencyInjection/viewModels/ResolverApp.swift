import shared

protocol ResolverAppProtocol: ResolverProtocol {
    
    @MainActor func resolve() -> FamilyListViewModel
    @MainActor func resolve() -> SettingsSharedViewModel
}

class ResolverApp: ResolverAppProtocol {}

extension ResolverAppProtocol {
    
    @MainActor
    func resolve() -> FamilyListViewModel {
        
        FamilyListViewModel(
            createFamilyListUseCase: KoinApplication.shared.inject(),
            getAllFamilyListUseCase: KoinApplication.shared.inject(),
            updateFamilyListUseCase: KoinApplication.shared.inject(),
            deleteFamilyListUseCase: KoinApplication.shared.inject(),
            getOrCreateAccountFromLocalUuidUseCase: KoinApplication.shared.inject()
        )
    }

    @MainActor 
    func resolve() -> SettingsSharedViewModel {

        SettingsSharedViewModel(
            getAccountUseCase: KoinApplication.shared.inject(),
            getLocalAccountUuidUseCase: KoinApplication.shared.inject(),
            setSharedAccountByCodeUseCase: KoinApplication.shared.inject()
        )
    }
}
