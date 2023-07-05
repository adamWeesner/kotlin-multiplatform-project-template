package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Index
 */
data class Index(
    /**
     * Index Key.
     */
    @SerialName("key")
    val key: String,

    /**
     * Index type.
     */
    @SerialName("type")
    val type: String,

    /**
     * Index status. Possible values: `available`, `processing`, `deleting`, `stuck`, or `failed`
     */
    @SerialName("status")
    val status: String,

    /**
     * Index attributes.
     */
    @SerialName("attributes")
    val attributes: List<Any>,

    /**
     * Index orders.
     */
    @SerialName("orders")
    var orders: List<Any>?,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "key" to key as Any,
        "type" to type as Any,
        "status" to status as Any,
        "attributes" to attributes as Any,
        "orders" to orders as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = Index(
            key = map["key"] as String,
            type = map["type"] as String,
            status = map["status"] as String,
            attributes = map["attributes"] as List<Any>,
            orders = map["orders"] as? List<Any>?,
        )
    }
}