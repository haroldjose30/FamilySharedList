package dev.haroldjose.familysharedlist

import platform.UIKit.UIDevice

class IOSPlatform: IPlatform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): IPlatform = IOSPlatform()
actual val isDebug: Boolean = Platform.isDebugBinary