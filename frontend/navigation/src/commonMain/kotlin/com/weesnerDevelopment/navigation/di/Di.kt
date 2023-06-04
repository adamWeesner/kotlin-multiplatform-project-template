package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.navigation.DefaultRootComponent
import com.weesnerDevelopment.navigation.Navigator
import com.weesnerDevelopment.navigation.RootComponent
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

expect val componentContextModule: DI.Module

val navModule by DI.Module {
    import(factoryModule)

    bindSingleton<Navigator> { instance<RootComponent>() }

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
