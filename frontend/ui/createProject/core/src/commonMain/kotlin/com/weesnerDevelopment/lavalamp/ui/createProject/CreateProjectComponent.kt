package com.weesnerDevelopment.lavalamp.ui.createProject

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.InputData
import com.weesnerDevelopment.lavalamp.frontend.resources.strings.Strings
import com.weesnerDevelopment.lavalamp.sdk.Feeling

interface CreateProjectComponent {
    val state: Value<State>

    fun updateData(data: State.() -> State)

    fun updateProjectName(name: String)
    fun updateNotes(notes: String)
    fun updateFeelingsNotes(notes: String)

    fun uiAction(action: UiAction?)
    fun save()

    data class State(
        val projectName: InputData = InputData(
            value = "",
            errorMessage = {
                if (it.isEmpty()) Strings.BlankProjectName
                else Strings.InvalidProjectName(it)
            }
        ),
        val notes: InputData = InputData(
            value = "",
            errorMessage = { "" } // cannot have an error is an optional input with no validations
        ),
        val feelings: List<Feeling> = emptyList(),
        val feelingsNotes: InputData = InputData(
            value = "",
            errorMessage = { "" } // cannot have an error is an optional input with no validations
        ),
        val effort: Float = 0f,

        val uiAction: UiAction? = null,

        val allFeelings: List<Feeling> = emptyList()
    ) {
        val hasInputError: Boolean
            get() = projectName.hasError || notes.hasError || feelingsNotes.hasError

        val saveEnabled: Boolean
            get() = !hasInputError && projectName.value.isNotBlank()

    }

    sealed interface UiAction {
        object SaveFailure : UiAction
        object SaveSuccess : UiAction
    }
}
