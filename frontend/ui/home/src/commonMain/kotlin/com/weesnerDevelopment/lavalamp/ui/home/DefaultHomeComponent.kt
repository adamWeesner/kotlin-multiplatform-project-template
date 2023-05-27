package com.weesnerDevelopment.lavalamp.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val projectRepository: ProjectRepository,
    override val onCreateProject: () -> Unit,
    override val onProjectDetails: (projectId: String) -> Unit,
) : HomeComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<HomeComponent.State> = MutableValue(HomeComponent.State())
    override val state: Value<HomeComponent.State> = _state

    override fun getAllProjects() {
        scope.launch {
            projectRepository.getAll {
                onSuccess { projects ->
                    _state.update {
                        it.copy(projects = projects)
                    }
                }
                onFailure {
                    // todo what do we do here
                }
            }
        }
    }
}