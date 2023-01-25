package dev.haroldjose.sharedfamilylist

class AndroidPlatform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): IPlatform = AndroidPlatform()

actual val isDebug = BuildConfig.DEBUG