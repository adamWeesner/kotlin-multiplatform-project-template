package io.appwrite.models

import io.appwrite.extensions.jsonCast
import kotlinx.serialization.SerialName

/**
 * Preferences
 */
data class Preferences<T>(
    /**
     * Additional properties
     */
    @SerialName("data")
    val data: T
) {
    fun toMap(): Map<String, Any> = mapOf(
        "data" to data!!.jsonCast<Map<String, Any>>()
    )

    companion object {
        operator fun invoke(
            data: Map<String, Any>
        ) = Preferences<Map<String, Any>>(
            data
        )

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> from(
            map: Map<String, Any>,
            nestedType: Class<T>
        ) = Preferences<T>(
            data = map.jsonCast<T>()
        )
    }
}