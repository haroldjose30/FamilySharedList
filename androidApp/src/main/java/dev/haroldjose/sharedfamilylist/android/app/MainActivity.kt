package dev.haroldjose.sharedfamilylist.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import dev.haroldjose.sharedfamilylist.android.MyApplicationTheme
import dev.haroldjose.sharedfamilylist.android.pages.familyList.FamilyListPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FamilyListPage()
                }
            }
        }
    }
}
