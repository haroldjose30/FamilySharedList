package dev.haroldjose.familysharedlist.android.services.firebase

import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseCrashlytics

class FirebaseCrashlytics: IFirebaseCrashlytics {

    private val crashlytics = Firebase.crashlytics
    override fun setCustomValue(value: Int, forKey: String) {
        crashlytics.setCustomKey(forKey, value)
    }

    override fun setCustomValue(value: String, forKey: String) {
        crashlytics.setCustomKey(forKey, value)
    }

    override fun setCustomValue(value: Boolean, forKey: String) {
        crashlytics.setCustomKey(forKey, value)
    }

    override fun setCustomValue(value: Double, forKey: String) {
        crashlytics.setCustomKey(forKey, value)
    }

    override fun setCustomKeysAndValues(keysAndValues: Map<String, Any>) {

        var keysAndValuesBuilder = CustomKeysAndValues.Builder()
        keysAndValues.forEach { (key, value) ->
            when (value) {
                is Int -> keysAndValuesBuilder.putInt(key, value)
                is String -> keysAndValuesBuilder.putString(key, value)
                is Boolean -> keysAndValuesBuilder.putBoolean(key, value)
                is Double -> keysAndValuesBuilder.putDouble(key, value)
            }
        }

        val customKeysAndValues = keysAndValuesBuilder.build()

        crashlytics.setCustomKeys(customKeysAndValues)
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun setUserID(id: String) {
        crashlytics.setUserId(id)
    }

    override fun setCrashlyticsCollectionEnabled(enabled: Boolean) {
        crashlytics.setCrashlyticsCollectionEnabled(enabled)
    }

    override fun record(kError: Throwable) {
        crashlytics.recordException(kError)
    }
}