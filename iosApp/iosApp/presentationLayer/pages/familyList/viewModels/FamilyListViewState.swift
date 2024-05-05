enum FamilyListViewState: Equatable {
    case initial
    case loading
    case success
    case error(
        message: String,
        retryAction: (() -> Void)?
    )

    static func == (lhs: FamilyListViewState, rhs: FamilyListViewState) -> Bool {
        switch (lhs, rhs) {
        case (.initial, .initial):
            return true
        case (.loading, .loading):
            return true
        case (.success, .success):
            return true
        case (.error, .error):
            return true
        default:
            return false
        }
    }
}


