package com.weesnerDevelopment.lavalamp.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.InMemoryProjectRepository
import com.weesnerDevelopment.navigation.DefaultRootComponent
import com.weesnerDevelopment.navigation.RootContent
import kotlinx.coroutines.Dispatchers

fun main() = application {
    val lifecycle = LifecycleRegistry()

    val rootComponent = runOnUiThread {
        DefaultRootComponent(
            platform = Platform.Desktop,
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            projectRepository = InMemoryProjectRepository,
            coroutineContext = Dispatchers.IO,
            navContext = Dispatchers.Main,
        )
    }

    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            RootContent(rootComponent)
        }
    }
}
