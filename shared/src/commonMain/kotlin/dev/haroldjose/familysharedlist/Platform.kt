package dev.haroldjose.familysharedlist

interface IPlatform {
    val name: String
    val isDebug: Boolean
    fun generateUUID(): String
    fun openUrlOnDefaultBrowser(url: String)
    fun openShareOptionsWithText(text: String)
    fun setupCrashlytics()
}

expect fun getPlatform(): IPlatform