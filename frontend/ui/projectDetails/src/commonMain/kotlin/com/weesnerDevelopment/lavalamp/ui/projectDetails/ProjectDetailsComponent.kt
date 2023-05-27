package com.weesnerDevelopment.lavalamp.ui.projectDetails

import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.lavalamp.sdk.Project

interface ProjectDetailsComponent {
    val state: Value<State>

    val onGetFail: () -> Unit
    val onProjectDeleteSuccess: () -> Unit
    val onProjectDeleteFail: () -> Unit
    val onBack: () -> Unit

    fun deleteProject()

    data class State(
        val project: Project? = null,
    )
}
