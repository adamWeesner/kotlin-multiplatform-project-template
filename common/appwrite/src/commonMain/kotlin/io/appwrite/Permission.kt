package io.appwrite

class Permission {
    companion object {
        fun read(role: String): String = "read(\"${role}\")"

        fun write(role: String): String = "write(\"${role}\")"

        fun create(role: String): String = "create(\"${role}\")"

        fun update(role: String): String = "update(\"${role}\")"

        fun delete(role: String): String = "delete(\"${role}\")"
    }
}