package com.weesnerDevelopment.lavalamp.ui.createProject

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.sdk.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultCreateProjectComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
    private val projectRepository: ProjectRepository,
    override val onCreateSuccess: () -> Unit,
    override val onCreateFailed: () -> Unit,
) : CreateProjectComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<CreateProjectComponent.State> =
        MutableValue(CreateProjectComponent.State())
    override val state: Value<CreateProjectComponent.State> = _state

    override fun save(project: Project) {
        scope.launch {
            projectRepository.add(project) {
                onSuccess {
                    // todo pull in logging lib
                    println("Project created\n")
                    scope.launch(navContext) {
                        onCreateSuccess()
                    }
                }
                onFailure {
                    // todo pull in logging lib
                    println("An error occurred attempting to save project:\n $it")
                    scope.launch(navContext) {
                        onCreateFailed()
                    }
                }
            }
        }
    }
}
