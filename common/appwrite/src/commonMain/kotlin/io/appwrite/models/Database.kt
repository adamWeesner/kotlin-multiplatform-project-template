package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Database
 */
data class Database(
    /**
     * Database ID.
     */
    @SerialName("\$id")
    val id: String,

    /**
     * Database name.
     */
    @SerialName("name")
    val name: String,

    /**
     * Database creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,

    /**
     * Database update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "\$id" to id as Any,
        "name" to name as Any,
        "\$createdAt" to createdAt as Any,
        "\$updatedAt" to updatedAt as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = Database(
            id = map["\$id"] as String,
            name = map["name"] as String,
            createdAt = map["\$createdAt"] as String,
            updatedAt = map["\$updatedAt"] as String,
        )
    }
}