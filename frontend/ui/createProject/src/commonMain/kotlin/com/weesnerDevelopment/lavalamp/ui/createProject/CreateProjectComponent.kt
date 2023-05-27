package com.weesnerDevelopment.lavalamp.ui.createProject

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.sdk.Feeling
import com.weesnerDevelopment.lavalamp.sdk.Project

interface CreateProjectComponent {
    val state: Value<State>

    val onCreateFailed: () -> Unit
    val onCreateSuccess: () -> Unit

    fun save(project: Project)

    data class State(
        val projectName: String = "",
        val notes: String = "",
        val feelings: List<Feeling> = emptyList(),
        val feelingsNotes: String = "",
        val effort: UInt = 0u
    )
}
