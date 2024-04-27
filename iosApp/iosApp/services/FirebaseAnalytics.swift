import Foundation
import shared
import FirebaseAnalytics

class FirebaseAnalytics: IFirebaseAnalytics {

    func logEvent(name: String) {
        Analytics.logEvent(name, parameters: nil)
    }

    func logEvent(name: String, params: [String : Any]) {
       Analytics.logEvent(name, parameters: params)
    }
    
    func resetAnalyticsData() {
        Analytics.resetAnalyticsData()
    }
    
    func setAnalyticsCollectionEnabled(enabled: Bool) {
        Analytics.setAnalyticsCollectionEnabled(enabled)
    }
    
    func setSessionTimeoutDuration(milliseconds: Int64) {
        Analytics.setSessionTimeoutInterval(TimeInterval(milliseconds/1000))
    }
    
    func setUserId(id: String?) {
        Analytics.setUserID(id)
    }
    
    func setUserProperty(name: String, value: String) {
        Analytics.setUserProperty(value, forName: name)
    }
}
