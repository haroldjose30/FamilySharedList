import Foundation
import UIKit

class SceneDelegate: NSObject, UIWindowSceneDelegate {

    private let routerService = RouterService.shared

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {

        if let shortcutItem = connectionOptions.shortcutItem {
            _ = routerService.handleShortCutItem(shortcutItem)
        }
    }

    func windowScene(_ windowScene: UIWindowScene, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {

        _ = routerService.handleShortCutItem(shortcutItem)
        completionHandler(true)
    }
}
