package dev.haroldjose.familysharedlist.android.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dev.haroldjose.familysharedlist.androidContextForKmm

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        androidContextForKmm = this
        setContent {
            NavigationView()
        }
    }
}