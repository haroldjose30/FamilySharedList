package dev.haroldjose.familysharedlist.android.app

import android.app.Application
import android.content.Context
import dev.haroldjose.familysharedlist.android.dependencyInjection.androidModule
import dev.haroldjose.familysharedlist.android.dependencyInjection.nativeModule
import dev.haroldjose.familysharedlist.dependencyInjection.startDI
import dev.haroldjose.familysharedlist.getPlatform
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        getPlatform().setupCrashlytics()

        startDI(listOf(nativeModule, androidModule)) {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
        }
    }
}