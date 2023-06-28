import SwiftUI
import shared

@main
struct iOSApp: App {

    @Environment(\.scenePhase) private var scenePhase
    private let resolverApp = ResolverApp()

    init() {
        configureIsRunningUITests()
        KoinApplication.start()
    }

    var body: some Scene {

       WindowGroup {
           SettingsPage()
           //FamilyListPage(
           // viewModel: resolverApp.resolve()
           //)
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
