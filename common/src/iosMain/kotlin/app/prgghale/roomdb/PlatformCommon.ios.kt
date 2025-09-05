package app.prgghale.roomdb

import platform.UIKit.UIDevice

class IOSPlatform: platformCommon{
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): platformCommon {
   return IOSPlatform()
}