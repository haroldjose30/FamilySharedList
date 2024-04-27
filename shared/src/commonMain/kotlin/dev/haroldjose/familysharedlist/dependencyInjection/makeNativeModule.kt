package dev.haroldjose.familysharedlist.dependencyInjection

import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

typealias NativeInjectionFactory<T> = Scope.() -> T

fun makeNativeModule(
    firebaseAnalytics: NativeInjectionFactory<IFirebaseAnalytics>
): Module {
    return module {
        single { firebaseAnalytics() }
    }
}