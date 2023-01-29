package dev.haroldjose.sharedfamilylist.dependencyInjection

import dev.haroldjose.sharedfamilylist.dependencyInjection.modules.appModule
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

// called by iOS
fun initKoin() {

    startKoin {
        modules(appModule())
    }
}