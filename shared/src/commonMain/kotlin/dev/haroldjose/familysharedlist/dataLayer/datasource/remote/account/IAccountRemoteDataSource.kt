package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account

import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

interface IAccountRemoteDataSource {
    suspend fun createSampleDataForFirstAccess(
        uuid: String
    ): Boolean
    suspend fun setSharedAccountByCode(
        accountUuid: String,
        code: String
    ): Boolean

    suspend fun insert(item: AccountDto)
    suspend fun findAll(): List<AccountDto>
    suspend fun findBy(uuid: String): AccountDto?
    suspend fun update(item: AccountDto)
    suspend fun delete(uuid: String)
}