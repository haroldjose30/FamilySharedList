enum NavigatorViewState {
    case loading
    case success
    case Error(
        message: String,
        retryAction: (() -> Void)?
    )
}
