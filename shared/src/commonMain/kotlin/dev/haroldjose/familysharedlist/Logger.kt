package dev.haroldjose.familysharedlist

import co.touchlab.crashkios.crashlytics.CrashlyticsKotlin

expect class PlatformLogger() {
    var enabled: Boolean

    fun logDebug(tag: String, message: String)
    fun logError(tag: String, message: String)
    fun logError(tag: String, message: String, exception: Throwable)
}

object Logger {
    private val platformLogger = PlatformLogger()

    var enabled
        get() = platformLogger.enabled
        set(value) {
            platformLogger.enabled = value
        }

    fun d(tag: String, message: String){
        CrashlyticsKotlin.logMessage("$tag: $message")
        platformLogger.logDebug(tag, message)
    }

    fun e(tag: String, message: String, exception: Throwable? = null){
        CrashlyticsKotlin.logMessage("$tag: $message")
        exception?.let {
            CrashlyticsKotlin.sendHandledException(it)
            platformLogger.logError(tag, message, exception)
        } ?: run {
            platformLogger.logError(tag, message)
        }
    }
}