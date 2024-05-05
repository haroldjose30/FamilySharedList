package dev.haroldjose.familysharedlist.services.firebase

interface IFirebaseCrashlytics {
    fun setCustomValue(value: Int, forKey: String)
    fun setCustomValue(value: String, forKey: String)
    fun setCustomValue(value: Boolean, forKey: String)
    fun setCustomValue(value: Double, forKey: String)
    fun setCustomKeysAndValues(keysAndValues: Map<String,Any>)
    fun log(message: String)
    fun setUserID(id: String)
    fun record(kError: Throwable)
     fun setCrashlyticsCollectionEnabled(enabled: Boolean)
}