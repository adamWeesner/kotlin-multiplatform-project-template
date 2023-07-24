package com.weesnerDevelopment.compose.core

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
val LocalWindowSize = compositionLocalOf {
    WindowSizeClass.calculateFromSize(
        size = Size(0f, 0f),
        density = Density(0f),
    )
}
