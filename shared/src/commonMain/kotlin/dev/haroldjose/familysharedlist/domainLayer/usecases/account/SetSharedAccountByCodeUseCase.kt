package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository

class SetSharedAccountByCodeUseCase(
    private val accountRepository: IAccountRepository
) {
    @NativeCoroutines
    suspend fun execute(
        accountUuid: String,
        code: String
    ): Boolean {
        return accountRepository.setSharedAccountByCode(
            accountUuid = accountUuid,
            code = code
        )
    }
}