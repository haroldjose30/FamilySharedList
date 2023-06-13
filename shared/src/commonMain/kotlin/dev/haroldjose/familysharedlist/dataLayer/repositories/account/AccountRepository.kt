package dev.haroldjose.familysharedlist.dataLayer.repositories.account

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.AccountMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.IAccountMongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

internal class AccountRepository() : IAccountRepository {

    //TODO: add to DI
    val remoteDataSource: IAccountMongoDbDataApiDataSource = AccountMongoDbDataApiDataSource()

    override suspend fun insert(item: AccountDto) {
        remoteDataSource.insert(item)
    }

    override suspend fun findBy(uuid: String): AccountDto? {
        val httpResponse = remoteDataSource.findBy(uuid)

        //if (httpResponse.status.value in 200..299) {
        //    val mongoDbResponseDto = httpResponse.body<AccountFindByUuidResponseDto>()
        //    return mongoDbResponseDto.document
        //}
        return null
    }

    override suspend fun update(item: AccountDto) {
        remoteDataSource.update(item)
    }

    override suspend fun delete(uuid: String) {
        remoteDataSource.delete(uuid)
    }
}