package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account

import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountCreateSampleDataForFirstAccessPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountDeletePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountFindAllPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountFindByPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountInsertPostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.AccountUpdatePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.request.SetSharedAccountByCodePostRequest
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountCreateSampleDataForFirstAccessPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountDeletePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountFindAllPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountFindByPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountInsertPostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.AccountUpdatePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account.response.SetSharedAccountByCodePostResponse
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.ajhttpclient.AJHttpClient
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

class AccountRemoteDataSource: IAccountRemoteDataSource {
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

    override suspend fun insert(item: AccountDto) {
        val request = AccountInsertPostRequest(item)
        val response = client.send<AccountInsertPostResponse>(request)
        Logger.d("AccountRemoteDataSource.insert.insertedId",response?.insertedId ?: "")
    }

    override suspend fun findAll(): List<AccountDto> {
        val request = AccountFindAllPostRequest()
        val response = client.send<AccountFindAllPostResponse>(request)
        return response?.documents ?: emptyList()
    }

    override suspend fun findBy(uuid: String): AccountDto? {
        val request = AccountFindByPostRequest(uuid = uuid)
        val response = client.send<AccountFindByPostResponse>(request)
        return response?.document
    }

    override suspend fun update(item: AccountDto) {
        val request = AccountUpdatePostRequest(item)
        val response = client.send<AccountUpdatePostResponse>(request)
        Logger.d("AccountRemoteDataSource.update.matchedCount",response?.matchedCount.toString())
        Logger.d("AccountRemoteDataSource.update.modifiedCount",response?.modifiedCount.toString())
    }

    override suspend fun delete(uuid: String) {
        val request = AccountDeletePostRequest(uuid)
        val response = client.send<AccountDeletePostResponse>(request)
        Logger.d("AccountRemoteDataSource.delete.deletedCount",response?.deletedCount.toString())
    }
}

