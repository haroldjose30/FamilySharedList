import Foundation
import shared

var nativeModule: Koin_coreModule = MakeNativeModuleKt.makeNativeModule(
    firebaseAnalytics: { _ in FirebaseAnalytics() }, 
    firebaseCrashlytics: { _ in FirebaseCrashlytics() },
    keyValueStorageDataSource: { _ in KeyValueStorageDataSource() }
)
