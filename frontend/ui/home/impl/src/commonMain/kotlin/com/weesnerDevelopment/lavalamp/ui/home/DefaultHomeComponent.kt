package com.weesnerDevelopment.lavalamp.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val projectRepository: ProjectRepository,
) : HomeComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<HomeComponent.State> = MutableValue(HomeComponent.State())
    override val state: Value<HomeComponent.State> = _state

    override suspend fun getAllProjects() {
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

    override fun uiAction(action: HomeComponent.UiAction?) {
        _state.update { it.copy(uiAction = action) }
    }
}