package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.navigation.factory.*
import com.weesnerDevelopment.navigation.factory.DIScreenComponentFactory
import com.weesnerDevelopment.navigation.factory.DefaultBottomSheetComponentFactory
import com.weesnerDevelopment.navigation.factory.DefaultDialogComponentFactory
import com.weesnerDevelopment.navigation.factory.DefaultDrawerComponentFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal val factoryModule by DI.Module {
    import(homeComponentModule)
    import(createProjectComponentModule)
    import(projectDetailsComponentModule)

    bindSingleton<ScreenComponentFactory> {
        DIScreenComponentFactory(di = this.di)
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