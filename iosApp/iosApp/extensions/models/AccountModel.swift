import Foundation
import shared

extension AccountModel: Identifiable {

    public var id: String {
        self.uuid
    }
}

