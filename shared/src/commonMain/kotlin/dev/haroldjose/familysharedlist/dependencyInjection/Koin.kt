package dev.haroldjose.familysharedlist.dependencyInjection

import dev.haroldjose.familysharedlist.dependencyInjection.modules.sharedModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun startDI(
    nativeModules: List<Module>,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(nativeModules + sharedModule)
}