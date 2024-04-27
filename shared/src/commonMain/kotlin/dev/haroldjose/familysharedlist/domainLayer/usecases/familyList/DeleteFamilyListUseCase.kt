package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository

class DeleteFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {
    @NativeCoroutines
    suspend fun execute(uuid: String): Boolean {
        familyListRepository.delete(
            uuid = uuid
        )
        return true
    }
}