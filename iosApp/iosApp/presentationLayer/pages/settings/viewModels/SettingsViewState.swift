enum SettingsViewState {

    case initial(
        accountShortCodeForShareTitle: String,
        accountsSharedWithMeTitle: String,
        accountsSharedWithMeSubtitle: String
    )

    case loading

    case success(
        accountShortCodeForShareTitle: String,
        accountsSharedWithMeTitle: String,
        accountsSharedWithMeSubtitle: String
    )
    
    case error(
        message: String,
        retryAction: (() -> Void)?
    )
}
