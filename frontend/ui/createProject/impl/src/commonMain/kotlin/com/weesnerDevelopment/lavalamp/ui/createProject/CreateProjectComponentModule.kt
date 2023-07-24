package com.weesnerDevelopment.lavalamp.ui.createProject

import org.kodein.di.*

val createProjectComponentModule by DI.Module {
    bindProvider<CreateProjectComponent> {
        DefaultCreateProjectComponent(
            componentContext = instance(),
            coroutineContext = instance("general"),
            projectRepository = instance()
        )
    }
}
