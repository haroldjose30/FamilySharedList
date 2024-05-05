import Foundation

enum FamilyListPageTabEnum {
    case prioritized
    case pending
    case completed

    var value: Int {
        switch self {
        case .prioritized:
            return 0
        case .pending:
            return 1
        case .completed:
            return 2
        }
    }

    func isPrioritized() -> Bool {
        return self == .prioritized
    }

    func isPending() -> Bool {
        return self == .pending
    }

    func isCompleted() -> Bool {
        return self == .completed
    }

    static func getBy(value: Int) -> FamilyListPageTabEnum {
        switch value {
        case 0:
            return .prioritized
        case 1:
            return .pending
        case 2:
            return .completed
        default:
            return .pending
        }
    }
}
