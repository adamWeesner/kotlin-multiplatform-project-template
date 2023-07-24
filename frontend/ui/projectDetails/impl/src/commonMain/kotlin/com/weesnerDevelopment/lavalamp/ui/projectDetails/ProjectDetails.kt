package com.weesnerDevelopment.lavalamp.ui.projectDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator

@Composable
fun ProjectDetails(
    component: ProjectDetailsComponent,
    navigator: Navigator,
) {
    val state by component.state.subscribeAsState()

    when (state.uiAction) {
        ProjectDetailsComponent.UiAction.Back -> {
            navigator.back()
        }

        ProjectDetailsComponent.UiAction.GetDetailsFailure -> {
            navigator.bringToFront(Config.SampleForDialog)
        }

        ProjectDetailsComponent.UiAction.ProjectDeleteFailure -> {
            navigator.bringToFront(Config.SampleForDialog)
        }

        ProjectDetailsComponent.UiAction.ProjectDeleteSuccess -> {
            navigator.back()
        }

        null -> {
            // do nothing
        }
    }

    // todo make the screen
}
