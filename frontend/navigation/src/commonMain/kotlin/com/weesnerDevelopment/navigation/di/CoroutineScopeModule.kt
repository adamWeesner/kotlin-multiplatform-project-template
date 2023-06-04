package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.common.Platform
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

val coroutineScopeModule by DI.Module {
    bindSingleton<CoroutineContext>(tag = "general") {
        when (instance<Platform>("platform")) {
            Platform.Android,
            Platform.Desktop -> Dispatchers.IO

            Platform.Terminal -> Dispatchers.Unconfined
        }
    }

    bindSingleton<CoroutineContext>(tag = "navigation") {
        when (instance<Platform>("platform")) {
            Platform.Android,
            Platform.Desktop -> Dispatchers.Main

            Platform.Terminal -> Dispatchers.Unconfined
        }
    }
}
