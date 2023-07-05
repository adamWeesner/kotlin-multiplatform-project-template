package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Health Status
 */
data class HealthStatus(
    /**
     * Duration in milliseconds how long the health check took.
     */
    @SerialName("ping")
    val ping: Long,

    /**
     * Service status. Possible values can are: `pass`, `fail`
     */
    @SerialName("status")
    val status: String,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "ping" to ping as Any,
        "status" to status as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = HealthStatus(
            ping = (map["ping"] as Number).toLong(),
            status = map["status"] as String,
        )
    }
}