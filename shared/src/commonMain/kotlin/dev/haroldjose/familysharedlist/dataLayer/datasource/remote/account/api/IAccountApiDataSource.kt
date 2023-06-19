package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api

interface IAccountApiDataSource {
    suspend fun createSampleDataForFirstAccess(uuid: String): Boolean
}