package dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels

import dev.haroldjose.familysharedlist.domainLayer.usecases.account.GetOrCreateAccountFromLocalUuidUseCase

class NavigatorViewModel(
    private val getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
): INavigatorViewModel {
    override suspend fun checkIfNeedToCreateNewAccount() {
        getOrCreateAccountFromLocalUuidUseCase.execute()
    }
}