package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.request.AccountCreateSampleDataForFirstAccessPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.request.SetSharedAccountByCodePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.response.AccountCreateSampleDataForFirstAccessPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.api.response.SetSharedAccountByCodePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpClient

class AccountApiDataSource: IAccountApiDataSource {
    //todo: add DI
    private val client: AJHttpClient = AJHttpClient()
    override suspend fun createSampleDataForFirstAccess(uuid: String): Boolean {

        val request = AccountCreateSampleDataForFirstAccessPostRequest(
            uuid = uuid
        )
        val response = client.send<AccountCreateSampleDataForFirstAccessPostResponse>(request)
        return response?.dataCreated ?: false
    }

    override suspend fun setSharedAccountByCode(
        accountUuid: String,
        code: String
    ): Boolean {

        val request = SetSharedAccountByCodePostRequest(
            accountUuid = accountUuid,
            code = code
        )
        val response = client.send<SetSharedAccountByCodePostResponse>(request)
        return response?.result ?: false
    }
}

