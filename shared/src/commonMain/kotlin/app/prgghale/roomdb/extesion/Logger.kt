package app.prgghale.roomdb.extesion

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer // 確保這個 import 是 kotlinx.serialization.serializer

@Suppress("UNCHECKED_CAST", "FunctionName") // FunctionName for toJson convention
fun Any?.toJson(): String {
    if (this == null) {
        return "Failed to convert: object is null"
    }

    return try {
        //FIXME
        // 在 KMP 中使用 KClass (this::class) 而不是 Java Class (this::class.java)
//        val kClass = this::class
//        val serializer = serializer(this::class.java) as KSerializer<Any> // This cast is potentially unsafe
////        val serializer = serializer(kClass) as KSerializer<Any> // 仍然需要 suppressed UNCHECKED_CAST
//        json.encodeToString(serializer, this)
        return "{}"
    } catch (e: SerializationException) {
        if (this is Unit) {
            return "{}" // 或者 "kotlin.Unit"
        }
        "Failed to convert to JSON (SerializationException): ${e.message} for type ${this::class.simpleName}"
    } catch (e: IllegalArgumentException) {
        // 這個 catch 塊通常表示找不到對應類型的序列化器
        "Failed to convert to JSON (IllegalArgumentException - No serializer for type ${this::class.simpleName}?): ${e.message}"
    } catch (e: Exception) {
        "Failed to convert to JSON (Unexpected Error): ${e.message} for type ${this::class.simpleName}"
    }
}

val json = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
    // For handling polymorphic types or context-sensitive serialization,
    // you might configure 'serializersModule' here.
}