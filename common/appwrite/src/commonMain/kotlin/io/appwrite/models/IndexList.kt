package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Indexes List
 */
data class IndexList(
    /**
     * Total number of indexes documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of indexes.
     */
    @SerialName("indexes")
    val indexes: List<Index>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "indexes" to indexes.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = IndexList(
            total = (map["total"] as Number).toLong(),
            indexes = (map["indexes"] as List<Map<String, Any>>).map { Index.from(map = it) },
        )
    }
}