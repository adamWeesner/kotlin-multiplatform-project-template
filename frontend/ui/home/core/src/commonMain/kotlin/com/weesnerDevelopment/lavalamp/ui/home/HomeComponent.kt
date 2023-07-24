package com.weesnerDevelopment.lavalamp.ui.home

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.sdk.Project

interface HomeComponent {
    val state: Value<State>

    suspend fun getAllProjects()
    fun uiAction(action: UiAction?)

    data class State(
        val projects: List<Project> = emptyList(),
        val uiAction: UiAction? = null
    )

    sealed interface UiAction

    sealed interface Navigate: UiAction {
        object CreateProject : Navigate
        data class ProjectDetails(val id: String) : Navigate
    }
}
