
import SwiftUI


struct NavigatorView<ViewModel>: View where ViewModel: NavigatorViewModelProtocol {
    @ObservedObject var viewModel: ViewModel

//    private let resolverApp = ResolverApp()

    @State private var router: ViewRouter = ViewRouter.quickInsert

    var body: some View {
        NavigationView {
            switch router {
            case .familyList:
                Button("goToSetting") {
                    self.router = .settings
                }
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
        return iosApp.QuickInsertListPage(viewModel: viewModel)
    }

    private func SettingsPage(router: ViewRouter) -> some View {
        //TODO: add viewModel to DI
        let viewModel = SettingsViewModel(
            getAccountUseCase: KoinApplication.shared.inject(),
            getLocalAccountUuidUseCase: KoinApplication.shared.inject(),
            setSharedAccountByCodeUseCase: KoinApplication.shared.inject()
        )
        viewModel.goBack = { self.router = .familyList }
        return iosApp.SettingsPage(viewModel: viewModel)
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
