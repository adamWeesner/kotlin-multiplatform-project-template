package com.weesnerDevelopment.lavalamp.di

import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.projectRepositoryModule
import com.weesnerDevelopment.navigation.di.componentContextModule
import com.weesnerDevelopment.navigation.di.coroutineScopeModule
import com.weesnerDevelopment.navigation.di.navModule
import org.kodein.di.DI
import org.kodein.di.bindConstant

fun setupDI(
    platform: Platform
): DI = DI {
    bindConstant(tag = "platform") { platform }

    // navigation
    import(componentContextModule)
    import(coroutineScopeModule)
    import(navModule)

    // backend
    import(projectRepositoryModule)
}
