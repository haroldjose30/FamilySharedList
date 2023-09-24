package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IOSKeyValueStorageDataSource
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

class IOSPlatform: IPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    //FIXME: harold
    override val isDebug: Boolean = false //Platform.isDebugBinary
    override fun generateUUID(): String = NSUUID().UUIDString()
    override fun getKeyValueStorageDataSource(): IKeyValueStorageDataSource = IOSKeyValueStorageDataSource()

    override fun openUrlOnDefaultBrowser(url: String) {
        val urlIos = NSURL(string = url)
        UIApplication.sharedApplication.openURL(urlIos)
    }
    override fun openShareOptionsWithText(text: String) {
//TODO: implement
    }
}

actual fun getPlatform(): IPlatform = IOSPlatform()