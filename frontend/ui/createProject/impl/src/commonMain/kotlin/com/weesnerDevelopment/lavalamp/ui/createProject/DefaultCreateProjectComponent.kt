package com.weesnerDevelopment.lavalamp.ui.createProject

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.logging.Logger
import com.weesnerDevelopment.lavalamp.frontend.resources.randomUUID
import com.weesnerDevelopment.lavalamp.sdk.Feeling
import com.weesnerDevelopment.lavalamp.sdk.Project
import com.weesnerDevelopment.lavalamp.sdk.State
import korlibs.time.DateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class DefaultCreateProjectComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val projectRepository: ProjectRepository,
) : CreateProjectComponent, ComponentContext by componentContext {
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<CreateProjectComponent.State> = runBlocking {
        val feelings = Feeling.values().sortedBy { it.ordinal }
        MutableValue(CreateProjectComponent.State(allFeelings = feelings))
    }
    override val state: Value<CreateProjectComponent.State> = _state

    override fun updateData(data: CreateProjectComponent.State.() -> CreateProjectComponent.State) {
        _state.update { data(state.value) }
    }

    override fun updateProjectName(name: String) =
        _state.update {
            it.copy(projectName = it.projectName.copy(value = name, hasError = false))
        }

    override fun updateNotes(notes: String) =
        _state.update {
            it.copy(notes = it.notes.copy(value = notes, hasError = false))
        }

    override fun updateFeelingsNotes(notes: String) =
        _state.update {
            it.copy(feelingsNotes = it.feelingsNotes.copy(value = notes, hasError = false))
        }

    override fun uiAction(action: CreateProjectComponent.UiAction?) {
        _state.update { it.copy(uiAction = action) }
    }

    override fun save() {
        scope.launch {
            dataValidation()

            if (state.value.hasInputError)
                return@launch

            val now = DateTime.now().format("EEE, MMM dd yyyy HH:mm:ss z")
            val currentState = state.value
            val project = Project(
                id = randomUUID(),
                name = currentState.projectName.value,
                notes = currentState.notes.value,
                effort = currentState.effort.toUInt(),
                state = State.Started(
                    feelings = currentState.feelings,
                    notes = currentState.feelingsNotes.value,
                    date = now
                ),
                rewards = emptyList(),
                dueDate = null, // todo add date/time picker to ui
                goalDate = null, // todo add date/time picker to ui
                createdDate = now,
                lastUpdatedDate = now,
            )

            projectRepository.add(project) {
                onSuccess {
                    Logger.debug("Project created\n$project")
                    _state.update { it.copy(uiAction = CreateProjectComponent.UiAction.SaveSuccess) }
                }
                onFailure {
                    Logger.debug("An error occurred attempting to save project:\n $it")
                    _state.update { it.copy(uiAction = CreateProjectComponent.UiAction.SaveFailure) }
                }
            }
        }
    }

    private fun dataValidation() {
        val state = state.value

        if (state.projectName.value.isBlank())
            _state.update { it.copy(projectName = it.projectName.copy(hasError = true)) }
    }
}
