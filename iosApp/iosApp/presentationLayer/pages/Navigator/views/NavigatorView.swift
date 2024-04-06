
import SwiftUI


struct NavigatorView<ViewModel>: View where ViewModel: NavigatorViewModelProtocol {
    @StateObject var viewModel: ViewModel

//    private let resolverApp = ResolverApp()

    @State private var router: ViewRouter = ViewRouter.familyList

    var body: some View {
        NavigationView {
            switch router {
            case .familyList:
                FamilyListPage(router: router)
            case .settings:
                SettingsPage(router: router)
            case .quickInsert:
                QuickInsertListPage(router: router)
            }
        }.onAppear {
            Task { await viewModel.checkIfNeedToCreateNewAccount() }
        }
    }

    private func QuickInsertListPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = QuickInsertListViewModel(
            createFamilyListUseCase: KoinApplication.shared.inject()
        )
        viewModel.goToFamilyListPage = { self.router = .familyList }
        return Family_List.QuickInsertListPage(viewModel: viewModel)
    }

    private func SettingsPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = SettingsViewModel(
            getAccountUseCase: KoinApplication.shared.inject(),
            getLocalAccountUuidUseCase: KoinApplication.shared.inject(),
            setSharedAccountByCodeUseCase: KoinApplication.shared.inject()
        )
        viewModel.goBack = { self.router = .familyList }
        return Family_List.SettingsPage(viewModel: viewModel)
    }

    private func FamilyListPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = FamilyListViewModel(
            getAllFamilyListUseCase: KoinApplication.shared.inject(),
            createFamilyListUseCase: KoinApplication.shared.inject(),
            updateFamilyListUseCase: KoinApplication.shared.inject(),
            deleteFamilyListUseCase: KoinApplication.shared.inject(),
            getOrCreateAccountFromLocalUuidUseCase: KoinApplication.shared.inject(),
            getProductByCodeUseCase: KoinApplication.shared.inject()
        )
        viewModel.goToSetting = { self.router = .settings }
        viewModel.goToQuickInsert = { self.router = .quickInsert }
        return Family_List.FamilyListPage(viewModel: viewModel)
    }


}

enum ViewRouter {
    case familyList
    case settings
    case quickInsert
}

#Preview {
    NavigationView {
        NavigatorView(
            viewModel: NavigatorViewModelMock()
        )
    }
}
