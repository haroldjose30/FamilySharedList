package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.domainLayer.extensions.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class GetAccountByUuidUseCase(
    private val accountRepository: IAccountRepository
)  {

    @NativeCoroutines
    suspend fun execute(uuid: String): AccountModel? {

        return accountRepository.findBy(uuid = uuid)?.toModel()
    }
}