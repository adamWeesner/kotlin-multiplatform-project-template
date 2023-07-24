package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.lavalamp.ui.createProject.createProjectComponentModule
import com.weesnerDevelopment.lavalamp.ui.home.homeComponentModule
import com.weesnerDevelopment.lavalamp.ui.projectDetails.projectDetailsComponentModule
import com.weesnerDevelopment.navigation.factory.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

internal val factoryModule by DI.Module {
    import(homeComponentModule)
    import(createProjectComponentModule)
    import(projectDetailsComponentModule)

    bindSingleton<ScreenComponentFactory> {
        DIScreenComponentFactory(di)
    }

    bindSingleton<DialogComponentFactory> {
        DefaultDialogComponentFactory(di)
    }

    bindSingleton<DrawerComponentFactory> {
        DefaultDrawerComponentFactory(di)
    }

    bindSingleton<BottomSheetComponentFactory> {
        DefaultBottomSheetComponentFactory(di)
    }
}