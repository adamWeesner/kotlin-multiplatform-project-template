package com.weesnerDevelopment.lavalamp.ui.home

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val homeComponentModule by DI.Module {
    bindProvider<HomeComponent> {
        DefaultHomeComponent(
            componentContext = instance(),
            coroutineContext = instance("general"),
            projectRepository = instance(),
        )
    }
}