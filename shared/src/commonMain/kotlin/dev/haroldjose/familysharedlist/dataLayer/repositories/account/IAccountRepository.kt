package dev.haroldjose.familysharedlist.dataLayer.repositories.account

import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

interface IAccountRepository {
        suspend fun insert(item: AccountDto)
        suspend fun findBy(uuid: String): AccountDto?
        suspend fun update(item: AccountDto)
        suspend fun delete(uuid: String)
        suspend fun createSampleDataForFirstAccess(uuid: String): Boolean
}