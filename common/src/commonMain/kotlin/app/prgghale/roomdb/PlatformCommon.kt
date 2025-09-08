package app.prgghale.roomdb

interface platformCommon {
    val name: String
}

expect fun getPlatform(): platformCommon