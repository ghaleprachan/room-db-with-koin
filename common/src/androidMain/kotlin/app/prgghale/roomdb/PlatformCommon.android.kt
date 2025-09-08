package app.prgghale.roomdb
class AndroidPlatform : platformCommon {
    override val name: String = "${System.getProperty("java.vm.name")}  ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): platformCommon {
    return AndroidPlatform()
}