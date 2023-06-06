package dev.haroldjose.familysharedlist

import kotlin.native.concurrent.ThreadLocal

class GlobalState {

    @ThreadLocal
    companion object {

        var isRunningUITests = false
    }
}