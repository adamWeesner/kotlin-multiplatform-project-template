package com.weesnerDevelopment.navigation

import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetailsComponent

/**
 * Represents the thing that feeds data to the "screen". Think of this as the ViewModel initializer.
 */
sealed interface Child {
    class Home(val component: HomeComponent) : Screen
    class CreateProject(val component: CreateProjectComponent) : Screen
    class ProjectDetails(val component: ProjectDetailsComponent) : Screen

    // todo remove once we get a dialog
    object SampleForDialog: Dialog
    // todo remove once we get a bottomsheet
    object SampleForBottomSheet: BottomSheet
    // todo remove once we get a drawer
    object SampleForDrawer: Drawer

    sealed interface Screen: Child
    sealed interface Dialog: Child
    sealed interface BottomSheet: Child
    sealed interface Drawer: Child
}