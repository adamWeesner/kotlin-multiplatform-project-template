package com.weesnerDevelopment.navigation.factory

import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetailsComponent
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.ScreenConfig
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.kodein.di.provider

internal interface ScreenComponentFactory {
    fun create(config: ScreenConfig): Child.Screen
}

internal class DIScreenComponentFactory(
    override val di: DI
) : ScreenComponentFactory, DIAware {
    override fun create(config: ScreenConfig): Child.Screen {
        return when (config) {
            Config.CreateProject -> {
                val component by instance<CreateProjectComponent>()
                Child.CreateProject(component)
            }

            Config.Home -> {
                val component by instance<HomeComponent>()
                Child.Home(component)
            }

            is Config.ProjectDetails -> {
                val component: () -> ProjectDetailsComponent by provider(arg = config.projectId)
                Child.ProjectDetails(component())
            }
        }
    }
}
