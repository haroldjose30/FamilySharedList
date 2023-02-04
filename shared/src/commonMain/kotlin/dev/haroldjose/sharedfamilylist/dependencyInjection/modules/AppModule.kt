package dev.haroldjose.sharedfamilylist.dependencyInjection.modules

import dev.haroldjose.sharedfamilylist.GlobalState
import org.koin.core.module.Module

fun appModule(): List<Module> {

    if (GlobalState.isRunningUITests) {
        return listOf(commonTestModule, platformModule)
    } else {
        return listOf(commonModule, platformModule)
    }
}