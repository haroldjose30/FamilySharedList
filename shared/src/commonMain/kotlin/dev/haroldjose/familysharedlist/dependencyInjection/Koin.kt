package dev.haroldjose.familysharedlist.dependencyInjection

import dev.haroldjose.familysharedlist.dependencyInjection.modules.appModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// called by Android
fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {

    appDeclaration()
    modules(
        appModule()
    )
}

// Koin utilities for iOS injection
fun KoinApplication.Companion.start(): KoinApplication = initKoin {
    modules(appModule())
}