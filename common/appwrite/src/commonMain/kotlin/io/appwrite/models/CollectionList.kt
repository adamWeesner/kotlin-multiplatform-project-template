package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Collections List
 */
data class CollectionList(
    /**
     * Total number of collections documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of collections.
     */
    @SerialName("collections")
    val collections: List<Collection>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "collections" to collections.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = CollectionList(
            total = (map["total"] as Number).toLong(),
            collections = (map["collections"] as List<Map<String, Any>>).map { Collection.from(map = it) },
        )
    }
}