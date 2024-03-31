package dev.haroldjose.familysharedlist.domainLayer.usecases.account

import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.IKeyValueStorageRepository
import dev.haroldjose.familysharedlist.dataLayer.repositories.keyValueStorage.KeyValueStorageRepositoryEnum

class GetLocalAccountUuidUseCase(
    private val keyValueStorageRepository: IKeyValueStorageRepository
)  {

    suspend fun execute(): String {

        val accountUuid = keyValueStorageRepository.getString(
            key = KeyValueStorageRepositoryEnum.ACCOUNT_UUID
        )

        return accountUuid ?: ""
    }
}