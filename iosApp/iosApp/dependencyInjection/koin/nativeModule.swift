import Foundation
import shared

var nativeModule: Koin_coreModule = MakeNativeModuleKt.makeNativeModule(
    firebaseAnalytics: { _ in FirebaseAnalytics() }, 
    keyValueStorageDataSource: { _ in KeyValueStorageDataSource() }
)
