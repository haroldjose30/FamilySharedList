package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.domainLayer.extensions.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class CreateAnonymousAccountUseCase(
    private val accountRepository: IAccountRepository
)  {

    @NativeCoroutines
    suspend fun execute(uuid: String) {

        val accountModel = AccountModel(uuid = uuid)
        return accountRepository.insert(accountModel.toDto())
    }
}