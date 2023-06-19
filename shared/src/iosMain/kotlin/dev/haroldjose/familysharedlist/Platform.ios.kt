package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IOSKeyValueStorageDataSource
import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

class IOSPlatform: IPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): IPlatform = IOSPlatform()
//FIXME: harold
actual val isDebug: Boolean = false //Platform.isDebugBinary
actual fun generateUUID(): String = NSUUID().UUIDString()
actual fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource = IOSKeyValueStorageDataSource()