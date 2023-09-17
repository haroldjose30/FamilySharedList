
import SwiftUI

struct NavigationView: View {

    private let resolverApp = ResolverApp()

    @State private var page: ViewRouter = ViewRouter.home

    var body: some View {
        switch page {
        case .home:
            FamilyListPage(
                goToSetting: { page = .settings }
            )
        case .settings:
            SettingsPage(
                goBack: { page = .home }
            )
        }
    }
}

enum ViewRouter {
    case home
    case settings
}
