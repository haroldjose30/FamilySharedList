package dev.haroldjose.familysharedlist.dataLayer.repositories.account

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.IAccountRemoteDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

internal class AccountRepository(
    val accountRemoteDataSource: IAccountRemoteDataSource
) : IAccountRepository {

    override suspend fun insert(item: AccountDto) {
        accountRemoteDataSource.insert(item)
    }
    override suspend fun findBy(uuid: String): AccountDto? {
        return accountRemoteDataSource.findBy(uuid)
    }
    override suspend fun update(item: AccountDto) {
        accountRemoteDataSource.update(item)
    }
    override suspend fun delete(uuid: String) {
        accountRemoteDataSource.delete(uuid)
    }
    override suspend fun createSampleDataForFirstAccess(uuid: String): Boolean {
        return accountRemoteDataSource.createSampleDataForFirstAccess(uuid = uuid)
    }
    override suspend fun setSharedAccountByCode(
        accountUuid: String,
        code: String
    ): Boolean {
        return accountRemoteDataSource.setSharedAccountByCode(
            accountUuid = accountUuid,
            code = code
        )
    }
}