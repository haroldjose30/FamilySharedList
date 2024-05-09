
import Foundation
import UIKit
import FirebaseCore
import shared

class AppDelegate: NSObject, UIApplicationDelegate {

    private let routerService = RouterService.shared

    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
        Platform_iosKt.getPlatform().setupCrashlytics()
        configureIsRunningUITests()
        KoinKt.startDI(
            nativeModules: [nativeModule],
            appDeclaration: { _ in }
        )

        if let shortcutItem = launchOptions?[UIApplication.LaunchOptionsKey.shortcutItem] as? UIApplicationShortcutItem {
            _ = routerService.handleShortCutItem(shortcutItem)
        }

        return true
    }

    func application(_ application: UIApplication, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
        let handled = routerService.handleShortCutItem(shortcutItem)
        completionHandler(handled)
    }

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        if let shortcutItem = options.shortcutItem {
            _ = routerService.handleShortCutItem(shortcutItem)
        }
        let configuration = UISceneConfiguration(name: connectingSceneSession.configuration.name, sessionRole: connectingSceneSession.role)
        configuration.delegateClass = SceneDelegate.self
        return configuration
    }
}
