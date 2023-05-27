package com.weesnerDevelopment.lavalamp.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.arkivanov.decompose.defaultComponentContext
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.InMemoryProjectRepository
import com.weesnerDevelopment.navigation.DefaultRootComponent
import com.weesnerDevelopment.navigation.RootContent
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = DefaultRootComponent(
            platform = Platform.Android,
            componentContext = defaultComponentContext(),
            projectRepository = InMemoryProjectRepository,
            coroutineContext = Dispatchers.IO,
            navContext = Dispatchers.Main,
        )

        setContent {
            MaterialTheme {
                RootContent(rootComponent)
            }
        }
    }
}
