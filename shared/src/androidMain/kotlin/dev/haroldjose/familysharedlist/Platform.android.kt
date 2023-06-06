package dev.haroldjose.familysharedlist

import android.os.Build

class AndroidPlatform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): IPlatform = AndroidPlatform()
//FIXME: harold
actual val isDebug: Boolean = false//BuildConfig.DEBUG