package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.activate
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.ui.createProject.DefaultCreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.DefaultHomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.DefaultProjectDetailsComponent
import kotlin.coroutines.CoroutineContext

internal fun createChildScreen(
    config: ScreenConfig,
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    navContext: CoroutineContext,
    projectRepository: ProjectRepository,
    dialogNav: SlotNav<DialogConfig, Child.Dialog>,
    bottomSheetNav: SlotNav<BottomSheetConfig, Child.BottomSheet>,
    drawerNav: SlotNav<DrawerConfig, Child.Drawer>,
    bringToFront: (ScreenConfig) -> Unit,
    back: () -> Unit,
): Child.Screen = when (config) {
    Config.Home ->
        Child.Home(
            DefaultHomeComponent(
                componentContext = componentContext,
                coroutineContext = coroutineContext,
                projectRepository = projectRepository,
                onCreateProject = {
                    bringToFront(Config.CreateProject)
                },
                onProjectDetails = { projectId ->
                    bringToFront(Config.ProjectDetails(projectId))
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
                    back()
                },
                onCreateFailed = {
                    // todo need to show dialog or something here
                    dialogNav.navigator.activate(Config.SampleForDialog)
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
                    dialogNav.navigator.activate(Config.SampleForDialog)
                },
                onProjectDeleteSuccess = {
                    back()
                },
                onProjectDeleteFail = {
                    dialogNav.navigator.activate(Config.SampleForDialog)
                },
                onBack = {
                    back()
                }
            )
        )
}
