package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * Users List
 */
data class UserList<T>(
    /**
     * Total number of users documents that matched your query.
     */
    @SerialName("total")
    val total: Long,

    /**
     * List of users.
     */
    @SerialName("users")
    val users: List<User<T>>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "total" to total as Any,
        "users" to users.map { it.toMap() } as Any,
    )

    companion object {
        operator fun invoke(
            total: Long,
            users: List<User<Map<String, Any>>>,
        ) = UserList<Map<String, Any>>(
            total,
            users,
        )

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> from(
            map: Map<String, Any>,
            nestedType: Class<T>
        ) = UserList<T>(
            total = (map["total"] as Number).toLong(),
            users = (map["users"] as List<Map<String, Any>>).map {
                User.from(
                    map = it,
                    nestedType
                )
            },
        )
    }
}