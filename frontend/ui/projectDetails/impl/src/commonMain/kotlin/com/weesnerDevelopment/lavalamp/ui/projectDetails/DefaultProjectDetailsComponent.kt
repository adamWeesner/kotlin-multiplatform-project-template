package com.weesnerDevelopment.lavalamp.ui.projectDetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.logging.Logger
import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class DefaultProjectDetailsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val projectId: String,
    private val projectRepository: ProjectRepository,
) : ProjectDetailsComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<ProjectDetailsComponent.State> =
        runBlocking(coroutineContext) {
            var project: Project? = null

            when (val response = projectRepository.get(projectId)) {
                is Either.Success -> project = response.value
                is Either.Failure -> {
                    uiAction(ProjectDetailsComponent.UiAction.GetDetailsFailure)
                }
            }

            if (project == null) {
                uiAction(ProjectDetailsComponent.UiAction.GetDetailsFailure)
                MutableValue(ProjectDetailsComponent.State())
            } else {
                val seededState = ProjectDetailsComponent.State(
                    project = project
                )

                MutableValue(seededState)
            }
        }

    override val state: Value<ProjectDetailsComponent.State> = _state

    override fun deleteProject() {
        val project = state.value.project

        if (project == null) {
            uiAction(ProjectDetailsComponent.UiAction.ProjectDeleteFailure)
            return
        }

        scope.launch {
            projectRepository.delete(project.id) {
                onSuccess {
                    Logger.debug("Successfully deleted project")
                    uiAction(ProjectDetailsComponent.UiAction.ProjectDeleteSuccess)
                }
                onFailure {
                    Logger.debug("An error occurred attempting to save project:\n $it")
                    uiAction(ProjectDetailsComponent.UiAction.ProjectDeleteFailure)
                }
            }
        }
    }

    override fun uiAction(action: ProjectDetailsComponent.UiAction?) {
        _state.update { it.copy(uiAction = action) }
    }
}