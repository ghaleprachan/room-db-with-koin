package app.prgghale.roomdb.extesion

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Suppress("UNCHECKED_CAST")
fun Any?.toJson(): String {
    if (this == null) {
        return "Failed to convert: object is null"
    }

    return try {
        val serializer = serializer(this::class.java) as KSerializer<Any> // This cast is potentially unsafe
        json.encodeToString(serializer, this)
    } catch (e: SerializationException) {
        when (this) {
            is Unit -> return "{}"
        }
        "Failed to convert to JSON (SerializationException): ${e.message} for type ${this::class.simpleName}"
    } catch (e: IllegalArgumentException) {
        "Failed to convert to JSON (IllegalArgumentException - No serializer?): ${e.message} for type ${this::class.simpleName}"
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