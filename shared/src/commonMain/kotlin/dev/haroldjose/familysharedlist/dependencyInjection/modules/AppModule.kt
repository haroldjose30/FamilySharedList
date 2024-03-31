package dev.haroldjose.familysharedlist.dependencyInjection.modules

import dev.haroldjose.familysharedlist.GlobalState
import org.koin.core.module.Module

fun appModule(): List<Module> {

    if (GlobalState.isRunningUITests) {
        return listOf(commonTestModule, platformModule)
    } else {
        return listOf(commonModule, platformModule)
    }
}