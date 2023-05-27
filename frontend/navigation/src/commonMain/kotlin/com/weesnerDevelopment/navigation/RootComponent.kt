package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.ui.createProject.DefaultCreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.DefaultHomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.DefaultProjectDetailsComponent
import kotlin.coroutines.CoroutineContext

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    platform: Platform,
    private val projectRepository: ProjectRepository,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation: StackNavigation<Config> = when (platform) {
        Platform.Terminal -> CustomStackNavigation()
        Platform.Desktop, Platform.Android -> StackNavigation()
    }

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val stack: Value<ChildStack<Config, Child>> = _childStack

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child = when (config) {
        Config.Home ->
            Child.Home(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    projectRepository = projectRepository,
                    onCreateProject = {
                        navigation.bringToFront(Config.CreateProject)
                    },
                    onProjectDetails = { projectId ->
                        navigation.bringToFront(Config.ProjectDetails(projectId))
                    }
                )
            )

        Config.CreateProject ->
            Child.CreateProject(
                DefaultCreateProjectComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    navContext = navContext,
                    projectRepository = projectRepository,
                    onCreateSuccess = {
                        navigation.pop()
                    },
                    onCreateFailed = {
                        // todo need to show dialog or something here
                        navigation.pop()
                    }
                )
            )

        is Config.ProjectDetails ->
            Child.ProjectDetails(
                DefaultProjectDetailsComponent(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                    navContext = navContext,
                    projectRepository = projectRepository,
                    projectId = config.projectId,
                    onGetFail = {
                        // todo need to show a dialog or something here
                        navigation.pop()
                    },
                    onProjectDeleteSuccess = {
                        navigation.pop()
                    },
                    onProjectDeleteFail = {
                        // todo need to show a dialog or something here
                        navigation.pop()
                    },
                    onBack = {
                        navigation.pop()
                    }
                )
            )
    }
}
