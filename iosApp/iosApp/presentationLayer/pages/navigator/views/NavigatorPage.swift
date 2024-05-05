
import SwiftUI

struct NavigatorPage<NavViewModel>: View where NavViewModel: NavigatorViewModelProtocol {
    @StateObject var viewModel: NavViewModel

    @State private var router: ViewRouter = ViewRouter.familyList

    var body: some View {
        NavigationView {

            switch viewModel.viewState {
            case .loading:
                ProgressView()
            case .success:
                NavigatorView()
            case let .Error(message, retryAction):
                ErrorPage(message: message, retryAction: retryAction)
            }
        }.onAppear {
            Task { await viewModel.checkIfNeedToCreateNewAccount() }
        }
    }

    private func NavigatorView() -> some View {
        Group {
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
        }
    }

    private func QuickInsertListPage(router: ViewRouter) -> some View {
        let viewModel: QuickInsertListViewModel = ResolverApp().resolve()
        viewModel.goToFamilyListPage = { self.router = .familyList }
        return Family_List.QuickInsertListPage(viewModel: viewModel)
    }

    private func SettingsPage(router: ViewRouter) -> some View {
        let viewModel: SettingsViewModel = ResolverApp().resolve()
        viewModel.goBack = { self.router = .familyList }
        return Family_List.SettingsPage(viewModel: viewModel)
    }

    private func FamilyListPage(router: ViewRouter) -> some View {
        let viewModel: FamilyListViewModel = ResolverApp().resolve()
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
        NavigatorPage(
            viewModel: NavigatorViewModelMock()
        )
    }
}
