package dev.haroldjose.familysharedlist.android.services.firebase

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import dev.haroldjose.familysharedlist.services.firebase.IFirebaseAnalytics

class FirebaseAnalytics: IFirebaseAnalytics {

    private var analytics = Firebase.analytics
    override fun logEvent(name: String) {
        analytics.logEvent(name, null)
    }

    override fun logEvent(name: String, params: Map<String, Any>) {
        val bundle = params.toBundle()
        analytics.logEvent(name, bundle)
    }

    override fun setAnalyticsCollectionEnabled(enabled: Boolean) {
        analytics.setAnalyticsCollectionEnabled(enabled)
    }

    override fun setUserId(id: String?) {
        analytics.setUserId(id)
    }

    override fun setSessionTimeoutDuration(milliseconds: Long) {
        analytics.setSessionTimeoutDuration(milliseconds)
    }

    override fun resetAnalyticsData() {
        analytics.resetAnalyticsData()
    }

    override fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }
}

private fun Map<String, Any?>.toBundle(): Bundle = bundleOf(*this.toList().toTypedArray())