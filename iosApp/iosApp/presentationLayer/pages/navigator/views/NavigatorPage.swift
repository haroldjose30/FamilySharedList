
import SwiftUI

struct NavigatorPage<NavViewModel>: View where NavViewModel: NavigatorViewModelProtocol {
    @StateObject var viewModel: NavViewModel
    @EnvironmentObject var routerService: RouterService

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
            switch routerService.router {
            case .familyList:
                FamilyListPage()
            case .settings:
                SettingsPage()
            case .quickInsert:
                QuickInsertListPage()
            case .debug:
                Text("onDebug")
            }
        }
    }

    private func QuickInsertListPage() -> some View {
        let viewModel: QuickInsertListViewModel = ResolverApp().resolve()
        viewModel.goToFamilyListPage = { self.routerService.router = .familyList }
        return Family_List.QuickInsertListPage(viewModel: viewModel)
    }

    private func SettingsPage() -> some View {
        let viewModel: SettingsViewModel = ResolverApp().resolve()
        viewModel.goBack = { self.routerService.router = .familyList }
        return Family_List.SettingsPage(viewModel: viewModel)
    }

    private func FamilyListPage() -> some View {
        let viewModel: FamilyListViewModel = ResolverApp().resolve()
        viewModel.goToSetting = { self.routerService.router = .settings }
        viewModel.goToQuickInsert = { self.routerService.router = .quickInsert }
        return Family_List.FamilyListPage(viewModel: viewModel)
    }
}

#Preview {
    NavigationView {
        NavigatorPage(
            viewModel: NavigatorViewModelMock()
        )
    }
}
