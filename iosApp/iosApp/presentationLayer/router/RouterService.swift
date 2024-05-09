import Foundation
import UIKit

class RouterService: ObservableObject {
    private init() {}
    static let shared = RouterService()
    @Published var router: RouterType = .familyList

    func handleShortCutItem(_ shortcutItem: UIApplicationShortcutItem) -> Bool {
        if shortcutItem.type == RouterType.quickInsert.rawValue {
            router = .quickInsert
            return true
        }
        return false
    }
}




