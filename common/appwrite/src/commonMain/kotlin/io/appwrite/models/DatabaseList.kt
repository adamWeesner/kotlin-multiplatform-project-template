package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Databases List
 */
data class DatabaseList(
    /**
     * Total number of databases documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of databases.
     */
    @SerialName("databases")
    val databases: List<Database>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "databases" to databases.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = DatabaseList(
            total = (map["total"] as Number).toLong(),
            databases = (map["databases"] as List<Map<String, Any>>).map { Database.from(map = it) },
        )
    }
}