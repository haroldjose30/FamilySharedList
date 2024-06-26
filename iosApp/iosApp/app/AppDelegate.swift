
import Foundation
import UIKit
import FirebaseCore
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        Platform_iosKt.getPlatform().setupCrashlytics()
        configureIsRunningUITests()
        KoinKt.startDI(
            nativeModules: [nativeModule],
            appDeclaration: { _ in }
        )
        return true
    }
}
