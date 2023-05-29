package com.weesnerDevelopment.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

/**
 * Represents the "navigation arg(s)" to feed the "screen" with.
 */
sealed class Config : Parcelable {
    @Parcelize
    object Home : ScreenConfig()

    @Parcelize
    object CreateProject : ScreenConfig()

    @Parcelize
    data class ProjectDetails(
        val projectId: String
    ) : ScreenConfig()

    // todo remove once we get a dialog
    @Parcelize
    object SampleForDialog: DialogConfig()

    // todo remove once we get a bottom sheet
    @Parcelize
    object SampleForBottomSheet: BottomSheetConfig()

    // todo remove once we get a drawer
    @Parcelize
    object SampleForDrawer: DrawerConfig()
}

sealed class ScreenConfig : Config()
sealed class DialogConfig : Config()
sealed class BottomSheetConfig : Config()
sealed class DrawerConfig : Config()
