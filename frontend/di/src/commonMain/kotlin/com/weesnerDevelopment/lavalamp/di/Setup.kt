package com.weesnerDevelopment.lavalamp.di

import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.projectRepositoryModule
import com.weesnerDevelopment.navigation.componentContextModule
import com.weesnerDevelopment.navigation.coroutineScopeModule
import com.weesnerDevelopment.navigation.navModule
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
