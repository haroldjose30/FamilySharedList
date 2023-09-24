
import SwiftUI

struct NavigationView: View {

    private let resolverApp = ResolverApp()

    @State private var page: ViewRouter = ViewRouter.home

    var body: some View {
        switch page {
        case .home:
            FamilyListWithScannerPage(
                viewModel: resolverApp.resolve(), 
                goToSetting: { page = .settings }
            )
        case .settings:
            SettingsPage(
                viewModel: resolverApp.resolve(),
                goBack: { page = .home }
            )
        case .barcodeScanner:
            BarcodeScannerPage(
                onSuccess: { barcodeValue in
                    print("onSuccess: \(barcodeValue)")
                    page = .home
                }
            )
        }
    }
}

enum ViewRouter {
    case home
    case settings
    case barcodeScanner
}
