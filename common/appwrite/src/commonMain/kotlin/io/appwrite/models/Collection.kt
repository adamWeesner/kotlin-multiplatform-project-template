package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Collection
 */
data class Collection(
    /**
     * Collection ID.
     */
    @SerialName("\$id")
    val id: String,

    /**
     * Collection creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,

    /**
     * Collection update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,

    /**
     * Collection permissions. [Learn more about permissions](/docs/permissions).
     */
    @SerialName("\$permissions")
    val permissions: List<Any>,

    /**
     * Database ID.
     */
    @SerialName("databaseId")
    val databaseId: String,

    /**
     * Collection name.
     */
    @SerialName("name")
    val name: String,

    /**
     * Collection enabled.
     */
    @SerialName("enabled")
    val enabled: Boolean,

    /**
     * Whether document-level permissions are enabled. [Learn more about permissions](/docs/permissions).
     */
    @SerialName("documentSecurity")
    val documentSecurity: Boolean,

    /**
     * Collection attributes.
     */
    @SerialName("attributes")
    val attributes: List<Any>,

    /**
     * Collection indexes.
     */
    @SerialName("indexes")
    val indexes: List<Index>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "\$id" to id as Any,
        "\$createdAt" to createdAt as Any,
        "\$updatedAt" to updatedAt as Any,
        "\$permissions" to permissions as Any,
        "databaseId" to databaseId as Any,
        "name" to name as Any,
        "enabled" to enabled as Any,
        "documentSecurity" to documentSecurity as Any,
        "attributes" to attributes as Any,
        "indexes" to indexes.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = Collection(
            id = map["\$id"] as String,
            createdAt = map["\$createdAt"] as String,
            updatedAt = map["\$updatedAt"] as String,
            permissions = map["\$permissions"] as List<Any>,
            databaseId = map["databaseId"] as String,
            name = map["name"] as String,
            enabled = map["enabled"] as Boolean,
            documentSecurity = map["documentSecurity"] as Boolean,
            attributes = map["attributes"] as List<Any>,
            indexes = (map["indexes"] as List<Map<String, Any>>).map { Index.from(map = it) },
        )
    }
}