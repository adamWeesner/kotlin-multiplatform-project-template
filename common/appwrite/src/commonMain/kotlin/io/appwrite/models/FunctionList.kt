package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Functions List
 */
data class FunctionList(
    /**
     * Total number of functions documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of functions.
     */
    @SerialName("functions")
    val functions: List<Function>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "functions" to functions.map { it.toMap() } as Any,
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun from(
            map: Map<String, Any>,
        ) = FunctionList(
            total = (map["total"] as Number).toLong(),
            functions = (map["functions"] as List<Map<String, Any>>).map { Function.from(map = it) },
        )
    }
}