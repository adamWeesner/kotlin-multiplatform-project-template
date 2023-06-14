package com.weesnerDevelopment.compose.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.weesnerDevelopment.lavalamp.frontend.resources.R

@Composable
actual fun imagePainter(image: Image): Painter {
    val imageResource: Int = when (image) {
        Image.Cancel -> R.drawable.cancel
        Image.BackArrow -> R.drawable.backarrow
    }

    return painterResource(imageResource)
}