package dev.haroldjose.familysharedlist.domainLayer.usecases.familyList

import co.touchlab.crashkios.crashlytics.CrashlyticsKotlin
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import dev.haroldjose.familysharedlist.dataLayer.repositories.familyList.IFamilyListRepository

class DeleteFamilyListUseCase(
    private val familyListRepository: IFamilyListRepository
)  {
    @NativeCoroutines
    suspend fun execute(uuid: String): Boolean {

        CrashlyticsKotlin.setUserId("someUserId")
        CrashlyticsKotlin.logMessage("Some message")
        CrashlyticsKotlin.setCustomValue("someKey", "someValue")
        CrashlyticsKotlin.sendHandledException(Exception("DeleteFamilyListUseCase"))
        CrashlyticsKotlin.sendFatalException(Exception("DeleteFamilyListUseCase"))

        familyListRepository.delete(
            uuid = uuid
        )
        return true
    }
}