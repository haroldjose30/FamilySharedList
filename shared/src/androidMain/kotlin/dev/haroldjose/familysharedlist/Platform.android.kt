package dev.haroldjose.familysharedlist

import android.os.Build
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.AndroidKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import java.util.UUID

class AndroidPlatform : IPlatform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): IPlatform = AndroidPlatform()
//FIXME: harold
actual val isDebug: Boolean = false//BuildConfig.DEBUG

actual fun generateUUID() = UUID.randomUUID().toString()

actual fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource = AndroidKeyValueStorageDataSource()