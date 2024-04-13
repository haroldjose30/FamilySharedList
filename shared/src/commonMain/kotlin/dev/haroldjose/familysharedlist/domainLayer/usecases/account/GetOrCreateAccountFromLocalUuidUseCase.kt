package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepositoryEnum
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.getPlatform
import kotlinx.coroutines.delay


object Constants {
    const val ACCOUNT_PREFIX = "DbAcc"
}

class GetOrCreateAccountFromLocalUuidUseCase(
    private val keyValueStorageRepository: IKeyValueStorageRepository,
    private val accountRepository: IAccountRepository,
)  {

    suspend fun execute(): AccountModel {

        val accountUuid = getOrCreateUuid()
        accountRepository.findBy(uuid = accountUuid)?.toModel()?.let {
            //TODO: verify if accountsSharedWithMe was revoked
            val defaultAccountSharedWithMe = it.accountsSharedWithMe.firstOrNull()
            val selectedDatabaseName: String = defaultAccountSharedWithMe ?: it.uuid
            keyValueStorageRepository.put(KeyValueStorageRepositoryEnum.SELECTED_DATABASE_NAME, selectedDatabaseName)
            return it
        }

        val accountModel = AccountModel(uuid = accountUuid)
        accountRepository.insert(accountModel.toDto())
        keyValueStorageRepository.put(KeyValueStorageRepositoryEnum.SELECTED_DATABASE_NAME, accountModel.uuid)

        //Generate SampleData for this new user
        accountRepository.createSampleDataForFirstAccess(uuid = accountUuid)

        return accountModel
    }

    /**
     * verify if exists an AccountUuid associated with this device
     * if not create one and store then on local
     */
    private fun getOrCreateUuid(): String {

        //try to get local UUID
        keyValueStorageRepository.getString(key = KeyValueStorageRepositoryEnum.ACCOUNT_UUID)?.let {
            if (it.isNotEmpty()) return it
        }

        //Generate new Account UUID for this device
        val sanitizedUuid = sanitizeUuid(getPlatform().generateUUID())
        var newAccountUuid = "${Constants.ACCOUNT_PREFIX}_${sanitizedUuid}"

        if (getPlatform().isDebug) {
            newAccountUuid = "${Constants.ACCOUNT_PREFIX}_DEBUG"
            Logger.d("GetOrCreateAccountFromLocalUuidUseCase","Debug Mode: Account UUID: $newAccountUuid")
        }

        //Save Account UUID on localStorage
        keyValueStorageRepository.put(
            key = KeyValueStorageRepositoryEnum.ACCOUNT_UUID,
            value = newAccountUuid
        )

        return newAccountUuid
    }

    private fun sanitizeUuid(uuid: String): String {
        return uuid.replace("-", "")
    }
}