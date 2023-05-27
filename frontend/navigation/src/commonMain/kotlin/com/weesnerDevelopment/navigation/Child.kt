package com.weesnerDevelopment.navigation

import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetailsComponent

/**
 * Represents the thing that feeds data to the "screen". Think of this as the ViewModel initializer.
 */
sealed interface Child {
    class Home(val component: HomeComponent) : Child
    class CreateProject(val component: CreateProjectComponent) : Child
    class ProjectDetails(val component: ProjectDetailsComponent) : Child
}