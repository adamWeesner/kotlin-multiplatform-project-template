package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Attributes List
 */
data class AttributeList(
    /**
     * Total number of attributes in the given collection.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of attributes.
     */
    @SerialName("attributes")
    val attributes: List<Any>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "attributes" to attributes as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = AttributeList(
            total = (map["total"] as Number).toLong(),
            attributes = map["attributes"] as List<Any>,
        )
    }
}