package dev.haroldjose.familysharedlist

import platform.Foundation.NSThread

actual class PlatformLogger {
    actual var enabled = true

    actual fun logDebug(tag: String, message: String){
        if(enabled) {
            println("$tag : $message : isMainThread(${NSThread.isMainThread})" )
        }
    }
    actual fun logError(tag: String, message: String){
        if(enabled) {
            println("$tag : $message : isMainThread(${NSThread.isMainThread})" )
        }
    }
    actual fun logError(tag: String, message: String, exception: Throwable){
        if(enabled) {
            println("$tag : $message : isMainThread(${NSThread.isMainThread})" )
            println(exception)
        }
    }
}