package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import androidx.lifecycle.ViewModel
import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase

class NavigatorViewModel(
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
): ViewModel(), INavigatorViewModel {
    override suspend fun checkIfNeedToCreateNewAccount() {
        getOrCreateAccountFromLocalUuidUseCase.execute()
    }
}