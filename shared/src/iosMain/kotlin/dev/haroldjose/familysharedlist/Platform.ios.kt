package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IOSKeyValueStorageDataSource
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

class IOSPlatform: IPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): IPlatform = IOSPlatform()
//FIXME: harold
actual val isDebug: Boolean = false //Platform.isDebugBinary
actual fun generateUUID(): String = NSUUID().UUIDString()
actual fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource = IOSKeyValueStorageDataSource()

actual fun openUrlOnDefaultBrowser(url: String) {
    val urlIos = NSURL(string = url)
    UIApplication.sharedApplication.openURL(urlIos)
}
actual fun openShareOptionsWithText(text: String) {
//TODO: implement
}