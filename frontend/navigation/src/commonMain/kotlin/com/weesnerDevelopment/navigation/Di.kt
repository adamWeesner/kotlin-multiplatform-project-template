package com.weesnerDevelopment.navigation

import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.navigation.factory.*
import com.weesnerDevelopment.navigation.factory.DefaultBottomSheetComponentFactory
import com.weesnerDevelopment.navigation.factory.DefaultDialogComponentFactory
import com.weesnerDevelopment.navigation.factory.DefaultScreenComponentFactory
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

expect val componentContextModule: DI.Module

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

private val factoryModule by DI.Module {
    bindSingleton<ScreenComponentFactory> {
        DefaultScreenComponentFactory(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
            projectRepository = instance(),
        )
    }

    bindSingleton<DialogComponentFactory> {
        DefaultDialogComponentFactory(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
        )
    }

    bindSingleton<DrawerComponentFactory> {
        DefaultDrawerComponentFactory(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
        )
    }

    bindSingleton<BottomSheetComponentFactory> {
        DefaultBottomSheetComponentFactory(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
        )
    }
}

val navModule by DI.Module {
    import(factoryModule)

    bindSingleton<RootComponent> {
        DefaultRootComponent(
            componentContext = instance(),
            platform = instance("platform"),
            screenFactory = instance(),
            dialogFactory = instance(),
            drawerFactory = instance(),
            bottomSheetFactory = instance(),
        )
    }
}