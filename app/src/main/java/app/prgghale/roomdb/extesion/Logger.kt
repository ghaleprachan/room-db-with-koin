package app.prgghale.roomdb.extesion

import com.google.gson.Gson

fun Any?.toJson(): String {
    return Gson().newBuilder()
        .setPrettyPrinting()
        .create()
        .toJson(this ?: return "Failed to convert")
}