package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api

interface IAccountApiDataSource {
    suspend fun createSampleDataForFirstAccess(
        uuid: String
    ): Boolean
    suspend fun setSharedAccountByCode(
        accountUuid: String,
        code: String
    ): Boolean
}