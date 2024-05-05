import shared

protocol ResolverAppProtocol {}

@MainActor
class ResolverApp: ResolverAppProtocol {}


extension ResolverAppProtocol {

    func resolve() -> NavigatorViewModel {
        NavigatorViewModel(
            getOrCreateAccountFromLocalUuidUseCase: koinInject(), 
            crashlytics: koinInject()
        )
    }

    func resolve() -> QuickInsertListViewModel {
        QuickInsertListViewModel(
            createFamilyListUseCase: koinInject(),
            crashlytics: koinInject()
        )
    }

    func resolve() -> SettingsViewModel {
        SettingsViewModel(
            getAccountUseCase: koinInject(),
            getLocalAccountUuidUseCase: koinInject(),
            setSharedAccountByCodeUseCase: koinInject(),
            crashlytics: koinInject()
        )
    }

    func resolve() -> FamilyListViewModel {
        FamilyListViewModel(
            getAllFamilyListUseCase: koinInject(),
            createFamilyListUseCase: koinInject(),
            updateFamilyListUseCase: koinInject(),
            deleteFamilyListUseCase: koinInject(),
            getOrCreateAccountFromLocalUuidUseCase: koinInject(),
            getProductByCodeUseCase: koinInject(),
            crashlytics: koinInject()
        )
    }
}
