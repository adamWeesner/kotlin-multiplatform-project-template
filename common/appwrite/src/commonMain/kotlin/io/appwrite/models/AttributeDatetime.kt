package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * AttributeDatetime
 */
data class AttributeDatetime(
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
     * ISO 8601 format.
     */
    @SerialName("format")
    val format: String,

    /**
     * Default value for attribute when not provided. Only null is optional
     */
    @SerialName("default")
    var default: String?,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "key" to key as Any,
        "type" to type as Any,
        "status" to status as Any,
        "required" to required as Any,
        "array" to array as Any,
        "format" to format as Any,
        "default" to default as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = AttributeDatetime(
            key = map["key"] as String,
            type = map["type"] as String,
            status = map["status"] as String,
            required = map["required"] as Boolean,
            array = map["array"] as? Boolean?,
            format = map["format"] as String,
            default = map["default"] as? String?,
        )
    }
}