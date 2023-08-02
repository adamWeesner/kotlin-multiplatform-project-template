package com.weesnerDevelopment.lavalamp.di

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.projectRepositoryModule
import com.weesnerDevelopment.navigation.di.coroutineScopeModule
import com.weesnerDevelopment.navigation.di.navModule
import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindSingleton

fun setupDI(
    platform: Platform,
    componentContext: ComponentContext
): DI = DI {
    bindConstant(tag = "platform") { platform }
    bindSingleton<ComponentContext> { componentContext }

    // navigation
    import(coroutineScopeModule)
    import(navModule)

    // backend
    import(projectRepositoryModule)
}
