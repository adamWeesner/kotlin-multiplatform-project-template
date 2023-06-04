package com.weesnerDevelopment.navigation.di

import com.weesnerDevelopment.lavalamp.ui.projectDetails.DefaultProjectDetailsComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetailsComponent
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

internal val projectDetailsComponentModule by DI.Module {
    bind<ProjectDetailsComponent> {
        factory { projectId: String ->
            val navigator: Navigator by di.instance<Navigator>()

            DefaultProjectDetailsComponent(
                componentContext = instance(),
                coroutineContext = instance("general"),
                navContext = instance("navigation"),
                projectRepository = instance(),
                projectId = projectId,
                onGetFail = {
                    navigator.bringToFront(Config.SampleForDialog)
                },
                onProjectDeleteSuccess = {
                    navigator.back()
                },
                onProjectDeleteFail = {
                    navigator.bringToFront(Config.SampleForDialog)
                },
                onBack = {
                    navigator.back()
                }
            )
        }
    }
}
