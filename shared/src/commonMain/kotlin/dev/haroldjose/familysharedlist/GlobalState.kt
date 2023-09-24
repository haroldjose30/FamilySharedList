package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel
import kotlin.native.concurrent.ThreadLocal

class GlobalState {

    @ThreadLocal
    companion object {

        var isRunningUITests = false
    }
}