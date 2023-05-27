package com.weesnerDevelopment.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * Represents the "navigation arg(s)" to feed the "screen" with.
 */
sealed class Config : Parcelable {
    @Parcelize
    object Home : Config()

    @Parcelize
    object CreateProject : Config()

    @Parcelize
    data class ProjectDetails(
        val projectId: String
    ) : Config()
}