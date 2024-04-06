package dev.haroldjose.familysharedlist.android.app

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import dev.haroldjose.familysharedlist.AndroidPlatform
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.views.NavigatorView
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidPlatform.androidContextForKmm = this
        AndroidPlatform.isDebuggable = (0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigatorView(viewModel = koinInject())
                }
            }
        }
    }
}