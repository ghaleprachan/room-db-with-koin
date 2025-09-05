package app.prgghale.roomdb

class JVMPlatform: platformCommon {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): platformCommon {
return JVMPlatform()
}