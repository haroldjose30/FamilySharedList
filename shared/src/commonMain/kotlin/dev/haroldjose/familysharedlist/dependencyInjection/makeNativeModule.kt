package dev.haroldjose.familysharedlist.dependencyInjection

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseCrashlytics
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

//reference: https://medium.com/@0x6368656174/kotlin-multiplatform-dependency-injection-with-pure-native-services-6897d9c3bcaa

typealias NativeInjectionFactory<T> = Scope.() -> T

fun makeNativeModule(
    firebaseAnalytics: NativeInjectionFactory<IFirebaseAnalytics>,
    firebaseCrashlytics: NativeInjectionFactory<IFirebaseCrashlytics>,
    keyValueStorageDataSource: NativeInjectionFactory<IKeyValueStorageDataSource>,
): Module {
    return module {
        single { firebaseAnalytics() }
        single { firebaseCrashlytics() }
        single { keyValueStorageDataSource() }
    }
}