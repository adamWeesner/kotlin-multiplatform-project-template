package com.weesnerDevelopment.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.FileResourceLoader
import androidx.compose.ui.res.painterResource
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun imagePainter(image: Image): Painter {
    val file = File("frontend/resources/src/desktopMain/resources")

    return painterResource(
        resourcePath = "${image.fileName}.svg",
        loader = FileResourceLoader(file)
    )
}