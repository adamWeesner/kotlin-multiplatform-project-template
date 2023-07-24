package com.weesnerDevelopment.lavalamp.desktop

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.compose.core.LocalWindowSize
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.RootComponent
import com.weesnerDevelopment.navigation.RootContent
import kimchi.Kimchi
import kimchi.logger.StandardWriter
import org.kodein.di.DI
import org.kodein.di.instance

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() = application {
    val di: DI by DI.lazy {
        extend(setupDI(Platform.Desktop))
    }

    Kimchi.addLog(StandardWriter)

    val rootComponent by di.instance<RootComponent>()

    Window(
        title = "lavalamp",
        onCloseRequest = ::exitApplication,
    ) {
        val windowSize = calculateWindowSizeClass()

        MaterialTheme {
            CompositionLocalProvider(LocalWindowSize provides windowSize) {
                RootContent(rootComponent)
            }
        }
    }
}
