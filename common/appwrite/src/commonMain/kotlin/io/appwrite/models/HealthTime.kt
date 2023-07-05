package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Health Time
 */
data class HealthTime(
    /**
     * Current unix timestamp on trustful remote server.
     */
    @SerialName("remoteTime")
    val remoteTime: Long,

    /**
     * Current unix timestamp of local server where Appwrite runs.
     */
    @SerialName("localTime")
    val localTime: Long,

    /**
     * Difference of unix remote and local timestamps in milliseconds.
     */
    @SerialName("diff")
    val diff: Long,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "remoteTime" to remoteTime as Any,
        "localTime" to localTime as Any,
        "diff" to diff as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = HealthTime(
            remoteTime = (map["remoteTime"] as Number).toLong(),
            localTime = (map["localTime"] as Number).toLong(),
            diff = (map["diff"] as Number).toLong(),
        )
    }
}