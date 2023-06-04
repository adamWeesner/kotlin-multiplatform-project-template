package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.createProject.DefaultCreateProjectComponent
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal val createProjectComponentModule by DI.Module {
    bindSingleton<CreateProjectComponent> {
        val navigator: Navigator by di.instance<Navigator>()

        DefaultCreateProjectComponent(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
            projectRepository = instance(),
            onCreateSuccess = {
                navigator.back()
            },
            onCreateFailed = {
                navigator.bringToFront(Config.SampleForDialog)
            }
        )
    }
}
