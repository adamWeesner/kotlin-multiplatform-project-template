package com.weesnerDevelopment.lavalamp.sdk

sealed interface Item {
    val id: String

    val createdDate: String
    val lastUpdatedDate: String

    interface Template : Item

    interface Stateful : Item {
        val state: State
    }

    interface Named : Item {
        val name: String
        val notes: String
    }
}

