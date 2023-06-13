package dev.haroldjose.familysharedlist.domainLayer.usecases.uuid

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.account.IAccountRepository
import dev.haroldjose.familysharedlist.domainLayer.extensions.toDto
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

class GetOrCreateUuidUseCase(
    private val accountRepository: IAccountRepository
)  {

    @NativeCoroutines
    suspend fun execute(): String {

        //TODO: try to get local UUID

        //TODO: Generate new UUID for this device

        //TODO: Save UUID on localSimpleStorage

        //TODO: return the UUID

        return ""
    }
}