package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class GetAccountUseCase(
    private val accountRepository: IAccountRepository
)  {

    suspend fun execute(accountUuid: String): AccountModel? {
        return accountRepository.findBy(uuid = accountUuid)?.toModel()
    }
}