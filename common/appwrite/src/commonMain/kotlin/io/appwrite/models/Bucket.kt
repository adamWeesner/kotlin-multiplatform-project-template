package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Bucket
 */
data class Bucket(
    /**
     * Bucket ID.
     */
    @SerialName("\$id")
    val id: String,

    /**
     * Bucket creation time in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,

    /**
     * Bucket update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,

    /**
     * Bucket permissions. [Learn more about permissions](/docs/permissions).
     */
    @SerialName("\$permissions")
    val permissions: List<Any>,

    /**
     * Whether file-level security is enabled. [Learn more about permissions](/docs/permissions).
     */
    @SerialName("fileSecurity")
    val fileSecurity: Boolean,

    /**
     * Bucket name.
     */
    @SerialName("name")
    val name: String,

    /**
     * Bucket enabled.
     */
    @SerialName("enabled")
    val enabled: Boolean,

    /**
     * Maximum file size supported.
     */
    @SerialName("maximumFileSize")
    val maximumFileSize: Long,

    /**
     * Allowed file extensions.
     */
    @SerialName("allowedFileExtensions")
    val allowedFileExtensions: List<Any>,

    /**
     * Compression algorithm choosen for compression. Will be one of none, [gzip](https://en.wikipedia.org/wiki/Gzip), or [zstd](https://en.wikipedia.org/wiki/Zstd).
     */
    @SerialName("compression")
    val compression: String,

    /**
     * Bucket is encrypted.
     */
    @SerialName("encryption")
    val encryption: Boolean,

    /**
     * Virus scanning is enabled.
     */
    @SerialName("antivirus")
    val antivirus: Boolean,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "\$id" to id as Any,
        "\$createdAt" to createdAt as Any,
        "\$updatedAt" to updatedAt as Any,
        "\$permissions" to permissions as Any,
        "fileSecurity" to fileSecurity as Any,
        "name" to name as Any,
        "enabled" to enabled as Any,
        "maximumFileSize" to maximumFileSize as Any,
        "allowedFileExtensions" to allowedFileExtensions as Any,
        "compression" to compression as Any,
        "encryption" to encryption as Any,
        "antivirus" to antivirus as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = Bucket(
            id = map["\$id"] as String,
            createdAt = map["\$createdAt"] as String,
            updatedAt = map["\$updatedAt"] as String,
            permissions = map["\$permissions"] as List<Any>,
            fileSecurity = map["fileSecurity"] as Boolean,
            name = map["name"] as String,
            enabled = map["enabled"] as Boolean,
            maximumFileSize = (map["maximumFileSize"] as Number).toLong(),
            allowedFileExtensions = map["allowedFileExtensions"] as List<Any>,
            compression = map["compression"] as String,
            encryption = map["encryption"] as Boolean,
            antivirus = map["antivirus"] as Boolean,
        )
    }
}