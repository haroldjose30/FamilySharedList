package dev.haroldjose.familysharedlist.android.app

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dev.haroldjose.familysharedlist.AndroidPlatform
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorPage
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent("app_started", null)
        AndroidPlatform.androidContextForKmm = this
        AndroidPlatform.isDebuggable = (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KoinAndroidContext() {
                        NavigatorPage()
                    }
                }
            }
        }
    }
}