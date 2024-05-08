import Foundation
import shared
import FirebaseCrashlytics

class FirebaseCrashlytics: IFirebaseCrashlytics {

    private var crashlytics = Crashlytics.crashlytics()

    func log(message: String) {
        crashlytics.log(message)
    }
    
    func record(kError: KotlinThrowable) {
        let error = kError.asError()
        crashlytics.record(error: error)
    }
    
    func setCrashlyticsCollectionEnabled(enabled: Bool) {
        crashlytics.setCrashlyticsCollectionEnabled(enabled)
    }
    
    func setCustomKeysAndValues(keysAndValues: [String : Any]) {
        crashlytics.setCustomKeysAndValues(keysAndValues)
    }
    
    func setCustomValue(value: Bool, forKey: String) {
        crashlytics.setCustomValue(value, forKey: forKey)
    }
    
    func setCustomValue(value: Double, forKey_ forKey: String) {
        crashlytics.setCustomValue(value, forKey: forKey)
    }
    
    func setCustomValue(value: Int32, forKey__ forKey: String) {
        crashlytics.setCustomValue(value, forKey: forKey)
    }
    
    func setCustomValue(value: String, forKey___ forKey: String) {
        crashlytics.setCustomValue(value, forKey: forKey)
    }
    
    func setUserID(id: String) {
        crashlytics.setUserID(id)
    }
}

extension IFirebaseCrashlytics {
    func record(error: Error) {
        Crashlytics.crashlytics().record(error: error)
    }
}
