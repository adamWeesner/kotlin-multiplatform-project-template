package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.lavalamp.ui.home.DefaultHomeComponent
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal val homeComponentModule by DI.Module {
    bindSingleton<HomeComponent> {
        val navigator: Navigator by di.instance<Navigator>()

        DefaultHomeComponent(
            componentContext = instance(),
            coroutineContext = instance("general"),
            projectRepository = instance(),
            onCreateProject = {
                navigator.bringToFront(Config.CreateProject)
            },
            onProjectDetails = { projectId ->
                navigator.bringToFront(Config.ProjectDetails(projectId))
            }
        )
    }
}