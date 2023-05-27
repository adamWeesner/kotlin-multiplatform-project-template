package com.weesnerDevelopment.lavalamp.ui.home

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.sdk.Project

interface HomeComponent {
    val state: Value<State>

    val onCreateProject: () -> Unit
    val onProjectDetails: (projectId: String) -> Unit

    fun getAllProjects()

    data class State(
        val projects: List<Project> = emptyList()
    )
}
