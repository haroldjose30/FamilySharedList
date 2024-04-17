package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay

class GetAccountUseCase(
    private val accountRepository: IAccountRepository
) {

    @NativeCoroutines
    suspend fun execute(accountUuid: String): AccountModel? {

        return accountRepository.findBy(uuid = accountUuid)?.toModel()
    }
}