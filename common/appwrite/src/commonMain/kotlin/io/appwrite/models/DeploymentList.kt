package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Deployments List
 */
data class DeploymentList(
    /**
     * Total number of deployments documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of deployments.
     */
    @SerialName("deployments")
    val deployments: List<Deployment>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "deployments" to deployments.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = DeploymentList(
            total = (map["total"] as Number).toLong(),
            deployments = (map["deployments"] as List<Map<String, Any>>).map { Deployment.from(map = it) },
        )
    }
}