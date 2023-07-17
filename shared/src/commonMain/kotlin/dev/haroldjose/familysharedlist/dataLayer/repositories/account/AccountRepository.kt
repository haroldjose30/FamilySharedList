package dev.haroldjose.familysharedlist.dataLayer.repositories.account

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.AccountApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.IAccountApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.mongoDb.AccountMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.mongoDb.IAccountMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

internal class AccountRepository() : IAccountRepository {
    //TODO: add to DI
    private val accountMongoDbDataApiDataSource: IAccountMongoDbDataApiDataSource = AccountMongoDbDataApiDataSource()
    private val accountApiDataSource: IAccountApiDataSource = AccountApiDataSource()
    override suspend fun insert(item: AccountDto) {
        accountMongoDbDataApiDataSource.insert(item)
    }
    override suspend fun findBy(uuid: String): AccountDto? {
        return accountMongoDbDataApiDataSource.findBy(uuid)
    }
    override suspend fun update(item: AccountDto) {
        accountMongoDbDataApiDataSource.update(item)
    }
    override suspend fun delete(uuid: String) {
        accountMongoDbDataApiDataSource.delete(uuid)
    }
    override suspend fun createSampleDataForFirstAccess(uuid: String): Boolean {
        return accountApiDataSource.createSampleDataForFirstAccess(uuid = uuid)
    }
    override suspend fun setSharedAccountByCode(
        accountUuid: String,
        code: String
    ): Boolean {
        return accountApiDataSource.setSharedAccountByCode(
            accountUuid = accountUuid,
            code = code
        )
    }
}