package dev.haroldjose.familysharedlist.android.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dev.haroldjose.familysharedlist.AndroidPlatform

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidPlatform.androidContextForKmm = this
        setContent {
            NavigationView()
        }
    }
}