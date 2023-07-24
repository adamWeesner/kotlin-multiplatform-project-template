package com.weesnerDevelopment.lavalamp.ui.projectDetails

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.sdk.Project

interface ProjectDetailsComponent {
    val state: Value<State>

    fun deleteProject()
    fun uiAction(action: UiAction?)

    data class State(
        val project: Project? = null,
        val uiAction: UiAction? = null,
    )

    sealed interface UiAction {
        object GetDetailsFailure : UiAction
        object ProjectDeleteSuccess : UiAction
        object ProjectDeleteFailure : UiAction
        object Back : UiAction
    }
}
