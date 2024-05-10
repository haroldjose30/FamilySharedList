import SwiftUI
import shared

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    @Environment(\.scenePhase) private var scenePhase
    private let routerService = RouterService.shared

    var body: some Scene {

        WindowGroup {
            NavigatorPage(viewModel: ResolverApp().resolve())
                .environmentObject(routerService)
        }.onChange(of: scenePhase) { phase in

            switch phase {
            case .background:
                print("scenePhase: background")
            case .inactive:
                print("scenePhase: inactive")
            case .active:
                print("scenePhase: active")
            @unknown default:
                print("scenePhase: @unknown")
            }
        }
    }
}
