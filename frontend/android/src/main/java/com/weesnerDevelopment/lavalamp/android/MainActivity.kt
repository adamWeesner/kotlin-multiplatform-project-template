package com.weesnerDevelopment.lavalamp.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.defaultComponentContext
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.compose.core.LocalWindowSize
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.RootComponent
import com.weesnerDevelopment.navigation.RootContent
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MainActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        extend(
            setupDI(
                platform = Platform.Android,
                componentContext = defaultComponentContext()
            )
        )
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent: RootComponent by di.instance()

        setContent {
            val windowSize = calculateWindowSizeClass()

            MaterialTheme {
                CompositionLocalProvider(LocalWindowSize provides windowSize) {
                    RootContent(rootComponent)
                }
            }
        }
    }
}
