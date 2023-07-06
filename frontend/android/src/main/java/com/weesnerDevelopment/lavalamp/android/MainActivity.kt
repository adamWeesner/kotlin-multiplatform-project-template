package com.weesnerDevelopment.lavalamp.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.RootComponent
import com.weesnerDevelopment.navigation.RootContent
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MainActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        extend(setupDI(Platform.Android))
    }

    private val rootComponent: RootComponent by di.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                RootContent(rootComponent)
            }
        }
    }
}
