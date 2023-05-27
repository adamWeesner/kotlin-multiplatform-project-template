package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetailsComponent

class ViewProject(
    private val component: ProjectDetailsComponent
) : SuspendingCliktCommand() {
    sealed class Option(
        val name: String
    ) {
        object DeleteProject : Option(name = "Delete project")
        object Back : Option(name = "Back to home")
    }

    private val details = with(component.state.value.project) {
        if (this == null) {
            "Failed to get project"
        } else {
            """
            Project:
             name: $name
             effort: $effort
             notes: $notes
             feelings: ${state.feelings.joinToString(", ")}
             feelings notes: ${state.notes}
            
        """.trimIndent()
        }
    }

    private val choices = buildMap<String, Option> {
        var currentIndex = 0

        put(currentIndex.toString(), Option.DeleteProject).also { currentIndex++ }

        put(currentIndex.toString(), Option.Back).also { currentIndex++ }
    }

    private val choicesList = choices.toList()
        .sortedBy { it.first }
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val action: Option by option()
        .choice(choices = choices)
        .prompt("$details$choicesList\nWhat would you like to do?")

    override suspend fun suspendRun() {
        when (action) {
            Option.DeleteProject -> {
                component.deleteProject()
            }

            Option.Back -> {
                component.onBack()
            }
        }
    }
}
