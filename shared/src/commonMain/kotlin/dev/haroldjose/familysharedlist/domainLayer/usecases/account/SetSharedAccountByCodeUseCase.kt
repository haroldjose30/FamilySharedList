package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class SetSharedAccountByCodeUseCase(
    private val accountRepository: IAccountRepository,
    private val firebaseAnalytics: IFirebaseAnalytics
) {
    @NativeCoroutines
    suspend fun execute(
        accountUuid: String,
        code: String
    ): Boolean {
        firebaseAnalytics.logEvent(IFirebaseAnalytics.Event.SET_SHARED_ACCOUNT_BY_CODE)
        return accountRepository.setSharedAccountByCode(
            accountUuid = accountUuid,
            code = code
        )
    }
}