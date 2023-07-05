package io.appwrite.extensions

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
}

fun Any.toJson(): String =
    json.encodeToString(this)

inline fun <reified T> String.fromJson(): T =
    json.decodeFromString<T>(this)

inline fun <reified T> Any.jsonCast(): T =
    json.decodeFromString<T>(toJson())
