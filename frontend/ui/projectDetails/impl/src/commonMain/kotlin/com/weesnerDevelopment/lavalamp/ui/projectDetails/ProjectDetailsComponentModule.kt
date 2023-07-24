package com.weesnerDevelopment.lavalamp.ui.projectDetails

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

val projectDetailsComponentModule by DI.Module {
    bind<ProjectDetailsComponent> {
        factory { projectId: String ->
            DefaultProjectDetailsComponent(
                componentContext = instance(),
                coroutineContext = instance("general"),
                projectRepository = instance(),
                projectId = projectId,
            )
        }
    }
}
