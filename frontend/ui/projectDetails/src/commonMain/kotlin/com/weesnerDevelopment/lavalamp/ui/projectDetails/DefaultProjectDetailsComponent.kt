package com.weesnerDevelopment.lavalamp.ui.projectDetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class DefaultProjectDetailsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
    private val projectId: String,
    private val projectRepository: ProjectRepository,
    override val onGetFail: () -> Unit,
    override val onProjectDeleteSuccess: () -> Unit,
    override val onProjectDeleteFail: () -> Unit,
    override val onBack: () -> Unit,
) : ProjectDetailsComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<ProjectDetailsComponent.State> =
        runBlocking(coroutineContext) {
            var project: Project? = null

            when (val response = projectRepository.get(projectId)) {
                is Either.Success -> project = response.value
                is Either.Failure -> onGetFail()
            }

            if (project == null) {
                onGetFail()
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
            onProjectDeleteFail()
            return
        }

        scope.launch {
            projectRepository.delete(project.id) {
                onSuccess {
                    // todo add logging lib
                    println("Successfully deleted project")
                    scope.launch(navContext) {
                        onProjectDeleteSuccess()
                    }
                }
                onFailure {
                    // todo add logging lib
                    println("An error occurred attempting to save project:\n $it")
                    scope.launch(navContext) {
                        onProjectDeleteFail()
                    }
                }
            }
        }
    }
}