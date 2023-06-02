package com.weesnerDevelopment.lavalamp.api.project

import org.kodein.di.DI
import org.kodein.di.bindSingleton

val projectRepositoryModule by DI.Module {
    bindSingleton<ProjectRepository> { InMemoryProjectRepository }
}