package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * AttributeInteger
 */
data class AttributeInteger(
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
     * Minimum value to enforce for new documents.
     */
    @SerialName("min")
    var min: Long?,

    /**
     * Maximum value to enforce for new documents.
     */
    @SerialName("max")
    var max: Long?,

    /**
     * Default value for attribute when not provided. Cannot be set when attribute is required.
     */
    @SerialName("default")
    var default: Long?,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "key" to key as Any,
        "type" to type as Any,
        "status" to status as Any,
        "required" to required as Any,
        "array" to array as Any,
        "min" to min as Any,
        "max" to max as Any,
        "default" to default as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = AttributeInteger(
            key = map["key"] as String,
            type = map["type"] as String,
            status = map["status"] as String,
            required = map["required"] as Boolean,
            array = map["array"] as? Boolean?,
            min = (map["min"] as? Number)?.toLong(),
            max = (map["max"] as? Number)?.toLong(),
            default = (map["default"] as? Number)?.toLong(),
        )
    }
}