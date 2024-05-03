enum SettingsViewState {

    case Initial(
        accountShortCodeForShareTitle: String,
        accountsSharedWithMeTitle: String,
        accountsSharedWithMeSubtitle: String
    )

    case loading

    case Success(
        accountShortCodeForShareTitle: String,
        accountsSharedWithMeTitle: String,
        accountsSharedWithMeSubtitle: String
    )
    
    case Error(
        message: String,
        retryAction: (() -> Void)?
    )
}
