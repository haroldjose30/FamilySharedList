import shared

protocol ResolverAppProtocol: ResolverProtocol {
    
    @MainActor func resolve() -> FamilyListSharedViewModel
    @MainActor func resolve() -> SettingsSharedViewModel
}

class ResolverApp: ResolverAppProtocol {}

extension ResolverAppProtocol {
    
    @MainActor
    func resolve() -> FamilyListSharedViewModel {

        FamilyListSharedViewModel(
            getAllFamilyListUseCase: KoinApplication.shared.inject(), 
            createFamilyListUseCase: KoinApplication.shared.inject(),
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
