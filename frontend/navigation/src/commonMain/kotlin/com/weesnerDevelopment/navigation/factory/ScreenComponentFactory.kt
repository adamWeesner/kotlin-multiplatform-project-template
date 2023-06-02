package com.weesnerDevelopment.navigation.factory

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.ui.createProject.DefaultCreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.DefaultHomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.DefaultProjectDetailsComponent
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import com.weesnerDevelopment.navigation.ScreenConfig
import org.kodein.di.DI
import kotlin.coroutines.CoroutineContext

interface ScreenComponentFactory {
    fun create(config: ScreenConfig, navigator: Navigator): Child.Screen
}

internal class DefaultScreenComponentFactory(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
    private val projectRepository: ProjectRepository,
) : ScreenComponentFactory {
    override fun create(config: ScreenConfig, navigator: Navigator): Child.Screen {
        return when (config) {
            Config.Home -> Child.Home(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    projectRepository = projectRepository,
                    onCreateProject = {
                        navigator.bringToFront(Config.CreateProject)
                    },
                    onProjectDetails = { projectId ->
                        navigator.bringToFront(Config.ProjectDetails(projectId))
                    }
                )
            )

            Config.CreateProject -> Child.CreateProject(
                DefaultCreateProjectComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    navContext = navContext,
                    projectRepository = projectRepository,
                    onCreateSuccess = {
                        navigator.back()
                    },
                    onCreateFailed = {
                        navigator.bringToFront(Config.SampleForDialog)
                    }
                )
            )

            is Config.ProjectDetails -> Child.ProjectDetails(
                DefaultProjectDetailsComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    navContext = navContext,
                    projectRepository = projectRepository,
                    projectId = config.projectId,
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
            )
        }
    }
}
