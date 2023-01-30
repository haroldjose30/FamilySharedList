package dev.haroldjose.sharedfamilylist.android.app

import android.app.Application
import dev.haroldjose.sharedfamilylist.android.dependencyInjection.androidModule
import dev.haroldjose.sharedfamilylist.dependencyInjection.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(appModule() + androidModule)
        }

    }
}