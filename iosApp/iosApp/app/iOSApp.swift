import SwiftUI
import shared

@main
struct iOSApp: App {

    @Environment(\.scenePhase) private var scenePhase
    

    init() {
        configureIsRunningUITests()
        KoinApplication.start()
    }

    var body: some Scene {

        WindowGroup {
            NavigationView()
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
