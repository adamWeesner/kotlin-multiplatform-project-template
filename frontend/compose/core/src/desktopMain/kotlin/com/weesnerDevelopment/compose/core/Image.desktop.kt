package com.weesnerDevelopment.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun imagePainter(image: Image): Painter = painterResource(image.fileName)