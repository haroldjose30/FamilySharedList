
import SwiftUI


struct NavigatorView<ViewModel>: View where ViewModel: NavigatorViewModelProtocol {
    @StateObject var viewModel: ViewModel

    //TODO: add viewModel to DI
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
            case .debug:
                Text("onDebug")
            }
        }.onAppear {
            Task { await viewModel.checkIfNeedToCreateNewAccount() }
        }
    }

    private func QuickInsertListPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = QuickInsertListViewModel(
            createFamilyListUseCase: koinInject()
        )
        viewModel.goToFamilyListPage = { self.router = .familyList }
        return Family_List.QuickInsertListPage(viewModel: viewModel)
    }

    private func SettingsPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = SettingsViewModel(
            getAccountUseCase: koinInject(),
            getLocalAccountUuidUseCase: koinInject(),
            setSharedAccountByCodeUseCase: koinInject()
        )
        viewModel.goBack = { self.router = .familyList }
        return Family_List.SettingsPage(viewModel: viewModel)
    }

    private func FamilyListPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = FamilyListViewModel(
            getAllFamilyListUseCase: koinInject(),
            createFamilyListUseCase: koinInject(),
            updateFamilyListUseCase: koinInject(),
            deleteFamilyListUseCase: koinInject(),
            getOrCreateAccountFromLocalUuidUseCase: koinInject(),
            getProductByCodeUseCase: koinInject()
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
    case debug
}

#Preview {
    NavigationView {
        NavigatorView(
            viewModel: NavigatorViewModelMock()
        )
    }
}
