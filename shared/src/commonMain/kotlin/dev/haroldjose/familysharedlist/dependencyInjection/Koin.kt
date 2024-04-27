package dev.haroldjose.familysharedlist.dependencyInjection

import dev.haroldjose.familysharedlist.dependencyInjection.modules.sharedModule
import org.koin.core.KoinApplication
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


// Koin utilities for iOS injection
fun KoinApplication.Companion.start(nativeModules: List<Module>): KoinApplication = startDI(nativeModules) {}