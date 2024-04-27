

import Foundation
import shared

var nativeModule: Koin_coreModule = MakeNativeModuleKt.makeNativeModule(
  firebaseAnalytics: { scope in
    return FirebaseAnalytics()
  }
)
