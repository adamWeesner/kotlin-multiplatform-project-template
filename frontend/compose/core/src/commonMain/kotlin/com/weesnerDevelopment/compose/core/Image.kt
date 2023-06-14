package com.weesnerDevelopment.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun imagePainter(image: Image): Painter

sealed class Image(val fileName: String) {
    object Cancel : Image(fileName = "cancel")
    object BackArrow : Image(fileName = "backArrow")
}
