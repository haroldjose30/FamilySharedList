package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.GlobalState
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.FamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepositoryEnum
import dev.haroldjose.familysharedlist.domainLayer.mappers.toDto
import dev.haroldjose.familysharedlist.domainLayer.mappers.toModel
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import dev.haroldjose.familysharedlist.generateUUID

class GetOrCreateAccountFromLocalUuidUseCase(
    private val keyValueStorageRepository: IKeyValueStorageRepository,
    private val accountRepository: IAccountRepository,
    private val familyListRepository: IFamilyListRepository
)  {

    private object Constants {
        const val ACCOUNT_PREFIX = "DbAcc"
    }

    @NativeCoroutines
    suspend fun execute(): AccountModel {

        val accountUuid = getOrCreateUuid()
        accountRepository.findBy(uuid = accountUuid)?.toModel()?.let {
            //configure singleton
            familyListRepository.setDataBase(databaseName = it.uuid)
            return it
        }

        val accountModel = AccountModel(uuid = accountUuid)
        accountRepository.insert(accountModel.toDto())
        //configure singleton
        familyListRepository.setDataBase(databaseName = accountModel.uuid)
        return accountModel
    }

    /**
     * verify if exists an AccountUuid associated with this device
     * if not create one and store then on local
     */
    private fun getOrCreateUuid(): String {

        //try to get local UUID
        val accountUuid = keyValueStorageRepository.getString(
            key = KeyValueStorageRepositoryEnum.ACCOUNT_UUID
        )

        accountUuid?.let {
            if (it.isNotEmpty()) {
                return it
            }
        }

        //Generate new Account UUID for this device
        val sanitizedUuid = sanitizeUuid(generateUUID())
        val newAccountUuid = "${Constants.ACCOUNT_PREFIX}_${sanitizedUuid}"

        //Save Account UUID on localStorage
        keyValueStorageRepository.put(
            key = KeyValueStorageRepositoryEnum.ACCOUNT_UUID,
            value = newAccountUuid
        )

        return newAccountUuid
    }

    private fun sanitizeUuid(uuid: String) : String {
        val sanitizedUuid = uuid.replace("-", "")
        return sanitizedUuid
    }
}