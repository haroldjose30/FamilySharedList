package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.services.firebase.FirebaseAnalytics
import dev.haroldjose.familysharedlist.dependencyInjection.makeNativeModule

val nativeModule = makeNativeModule(
    firebaseAnalytics = { FirebaseAnalytics() }
)


