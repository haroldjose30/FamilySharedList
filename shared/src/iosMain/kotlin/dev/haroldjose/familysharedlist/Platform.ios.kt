package dev.haroldjose.familysharedlist

import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IKeyValueStorageDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.local.keyValueStorage.IOSKeyValueStorageDataSource
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

class IOSPlatform: IPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean = Platform.isDebugBinary
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