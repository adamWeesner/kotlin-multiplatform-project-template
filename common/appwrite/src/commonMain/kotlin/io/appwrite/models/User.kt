package io.appwrite.models

import kotlinx.serialization.SerialName

/**
 * User
 */
data class User<T>(
    /**
     * User ID.
     */
    @SerialName("\$id")
    val id: String,

    /**
     * User creation date in ISO 8601 format.
     */
    @SerialName("\$createdAt")
    val createdAt: String,

    /**
     * User update date in ISO 8601 format.
     */
    @SerialName("\$updatedAt")
    val updatedAt: String,

    /**
     * User name.
     */
    @SerialName("name")
    val name: String,

    /**
     * Hashed user password.
     */
    @SerialName("password")
    var password: String?,

    /**
     * Password hashing algorithm.
     */
    @SerialName("hash")
    var hash: String?,

    /**
     * Password hashing algorithm configuration.
     */
    @SerialName("hashOptions")
    var hashOptions: Any?,

    /**
     * User registration date in ISO 8601 format.
     */
    @SerialName("registration")
    val registration: String,

    /**
     * User status. Pass `true` for enabled and `false` for disabled.
     */
    @SerialName("status")
    val status: Boolean,

    /**
     * Password update time in ISO 8601 format.
     */
    @SerialName("passwordUpdate")
    val passwordUpdate: String,

    /**
     * User email address.
     */
    @SerialName("email")
    val email: String,

    /**
     * User phone number in E.164 format.
     */
    @SerialName("phone")
    val phone: String,

    /**
     * Email verification status.
     */
    @SerialName("emailVerification")
    val emailVerification: Boolean,

    /**
     * Phone verification status.
     */
    @SerialName("phoneVerification")
    val phoneVerification: Boolean,

    /**
     * User preferences as a key-value object
     */
    @SerialName("prefs")
    val prefs: Preferences<T>,
) {
    fun toMap(): Map<String, Any> = mapOf(
        "\$id" to id as Any,
        "\$createdAt" to createdAt as Any,
        "\$updatedAt" to updatedAt as Any,
        "name" to name as Any,
        "password" to password as Any,
        "hash" to hash as Any,
        "hashOptions" to hashOptions as Any,
        "registration" to registration as Any,
        "status" to status as Any,
        "passwordUpdate" to passwordUpdate as Any,
        "email" to email as Any,
        "phone" to phone as Any,
        "emailVerification" to emailVerification as Any,
        "phoneVerification" to phoneVerification as Any,
        "prefs" to prefs.toMap() as Any,
    )

    companion object {
        operator fun invoke(
            id: String,
            createdAt: String,
            updatedAt: String,
            name: String,
            password: String?,
            hash: String?,
            hashOptions: Any?,
            registration: String,
            status: Boolean,
            passwordUpdate: String,
            email: String,
            phone: String,
            emailVerification: Boolean,
            phoneVerification: Boolean,
            prefs: Preferences<Map<String, Any>>,
        ) = User<Map<String, Any>>(
            id,
            createdAt,
            updatedAt,
            name,
            password,
            hash,
            hashOptions,
            registration,
            status,
            passwordUpdate,
            email,
            phone,
            emailVerification,
            phoneVerification,
            prefs,
        )

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> from(
            map: Map<String, Any>,
            nestedType: Class<T>
        ) = User<T>(
            id = map["\$id"] as String,
            createdAt = map["\$createdAt"] as String,
            updatedAt = map["\$updatedAt"] as String,
            name = map["name"] as String,
            password = map["password"] as? String?,
            hash = map["hash"] as? String?,
            hashOptions = map["hashOptions"] as? Any?,
            registration = map["registration"] as String,
            status = map["status"] as Boolean,
            passwordUpdate = map["passwordUpdate"] as String,
            email = map["email"] as String,
            phone = map["phone"] as String,
            emailVerification = map["emailVerification"] as Boolean,
            phoneVerification = map["phoneVerification"] as Boolean,
            prefs = Preferences.from(map = map["prefs"] as Map<String, Any>, nestedType),
        )
    }
}