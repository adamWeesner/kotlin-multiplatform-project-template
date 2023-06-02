package com.weesnerDevelopment.lavalamp.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.RootComponent
import com.weesnerDevelopment.navigation.RootContent
import org.kodein.di.DI
import org.kodein.di.instance

fun main() = application {
    val di: DI by DI.lazy {
        extend(setupDI(Platform.Desktop))
    }

    val rootComponent by di.instance<RootComponent>()

    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            RootContent(rootComponent)
        }
    }
}