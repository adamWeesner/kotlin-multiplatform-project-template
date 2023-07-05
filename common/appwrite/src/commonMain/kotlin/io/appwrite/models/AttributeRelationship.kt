package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * AttributeRelationship
 */
data class AttributeRelationship(
    /**
     * Attribute Key.
     */
    @SerialName("key")
    val key: String,

    /**
     * Attribute type.
     */
    @SerialName("type")
    val type: String,

    /**
     * Attribute status. Possible values: `available`, `processing`, `deleting`, `stuck`, or `failed`
     */
    @SerialName("status")
    val status: String,

    /**
     * Is attribute required?
     */
    @SerialName("required")
    val required: Boolean,

    /**
     * Is attribute an array?
     */
    @SerialName("array")
    var array: Boolean?,

    /**
     * The ID of the related collection.
     */
    @SerialName("relatedCollection")
    val relatedCollection: String,

    /**
     * The type of the relationship.
     */
    @SerialName("relationType")
    val relationType: String,

    /**
     * Is the relationship two-way?
     */
    @SerialName("twoWay")
    val twoWay: Boolean,

    /**
     * The key of the two-way relationship.
     */
    @SerialName("twoWayKey")
    val twoWayKey: String,

    /**
     * How deleting the parent document will propagate to child documents.
     */
    @SerialName("onDelete")
    val onDelete: String,

    /**
     * Whether this is the parent or child side of the relationship
     */
    @SerialName("side")
    val side: String,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "key" to key as Any,
        "type" to type as Any,
        "status" to status as Any,
        "required" to required as Any,
        "array" to array as Any,
        "relatedCollection" to relatedCollection as Any,
        "relationType" to relationType as Any,
        "twoWay" to twoWay as Any,
        "twoWayKey" to twoWayKey as Any,
        "onDelete" to onDelete as Any,
        "side" to side as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = AttributeRelationship(
            key = map["key"] as String,
            type = map["type"] as String,
            status = map["status"] as String,
            required = map["required"] as Boolean,
            array = map["array"] as? Boolean?,
            relatedCollection = map["relatedCollection"] as String,
            relationType = map["relationType"] as String,
            twoWay = map["twoWay"] as Boolean,
            twoWayKey = map["twoWayKey"] as String,
            onDelete = map["onDelete"] as String,
            side = map["side"] as String,
        )
    }
}