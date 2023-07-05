package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Health Antivirus
 */
data class HealthAntivirus(
    /**
     * Antivirus version.
     */
    @SerialName("version")
    val version: String,

    /**
     * Antivirus status. Possible values can are: `disabled`, `offline`, `online`
     */
    @SerialName("status")
    val status: String,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "version" to version as Any,
        "status" to status as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = HealthAntivirus(
            version = map["version"] as String,
            status = map["status"] as String,
        )
    }
}