enum QuickInsertListViewState {
    case initial
    case loading
    case success
    case error(
        message: String,
        retryAction: (() -> Void)?
    )
}

