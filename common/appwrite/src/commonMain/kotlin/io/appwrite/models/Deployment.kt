package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Deployment
 */
data class Deployment(
    /**
     * Deployment ID.
     */
    @SerialName("\$id")
    val id: String,

    /**
     * Deployment creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,

    /**
     * Deployment update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,

    /**
     * Resource ID.
     */
    @SerialName("resourceId")
    val resourceId: String,

    /**
     * Resource type.
     */
    @SerialName("resourceType")
    val resourceType: String,

    /**
     * The entrypoint file to use to execute the deployment code.
     */
    @SerialName("entrypoint")
    val entrypoint: String,

    /**
     * The code size in bytes.
     */
    @SerialName("size")
    val size: Long,

    /**
     * The current build ID.
     */
    @SerialName("buildId")
    val buildId: String,

    /**
     * Whether the deployment should be automatically activated.
     */
    @SerialName("activate")
    val activate: Boolean,

    /**
     * The deployment status. Possible values are &quot;processing&quot;, &quot;building&quot;, &quot;pending&quot;, &quot;ready&quot;, and &quot;failed&quot;.
     */
    @SerialName("status")
    val status: String,

    /**
     * The build stdout.
     */
    @SerialName("buildStdout")
    val buildStdout: String,

    /**
     * The build stderr.
     */
    @SerialName("buildStderr")
    val buildStderr: String,

    /**
     * The current build time in seconds.
     */
    @SerialName("buildTime")
    val buildTime: Long,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "\$id" to id as Any,
        "\$createdAt" to createdAt as Any,
        "\$updatedAt" to updatedAt as Any,
        "resourceId" to resourceId as Any,
        "resourceType" to resourceType as Any,
        "entrypoint" to entrypoint as Any,
        "size" to size as Any,
        "buildId" to buildId as Any,
        "activate" to activate as Any,
        "status" to status as Any,
        "buildStdout" to buildStdout as Any,
        "buildStderr" to buildStderr as Any,
        "buildTime" to buildTime as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = Deployment(
            id = map["\$id"] as String,
            createdAt = map["\$createdAt"] as String,
            updatedAt = map["\$updatedAt"] as String,
            resourceId = map["resourceId"] as String,
            resourceType = map["resourceType"] as String,
            entrypoint = map["entrypoint"] as String,
            size = (map["size"] as Number).toLong(),
            buildId = map["buildId"] as String,
            activate = map["activate"] as Boolean,
            status = map["status"] as String,
            buildStdout = map["buildStdout"] as String,
            buildStderr = map["buildStderr"] as String,
            buildTime = (map["buildTime"] as Number).toLong(),
        )
    }
}