package dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels

sealed class SettingsViewState {

    class Initial(
        val accountShortCodeForShareTitle: String,
        val accountsSharedWithMeTitle: String,
        val accountsSharedWithMeSubtitle: String
    ) : SettingsViewState()
    object Loading: SettingsViewState()
    class Success(
        val accountShortCodeForShareTitle: String,
        val accountsSharedWithMeTitle: String,
        val accountsSharedWithMeSubtitle: String
    ) : SettingsViewState()
    data class Error(
        val message: String,
        val retryAction: (() -> Unit)? = null
    ) : SettingsViewState()
}