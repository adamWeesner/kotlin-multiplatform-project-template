package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.weesnerDevelopment.lavalamp.sdk.Feeling
import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import com.weesnerDevelopment.navigation.Navigator

class CreateProject(
    private val component: CreateProjectComponent,
    private val navigator: Navigator,
) : SuspendingCliktCommand() {
    init {
        component.state.subscribe { state ->
            when (state.uiAction) {
                CreateProjectComponent.UiAction.SaveFailure -> {
                    echo("Failed to save project, you need to try again.")
                    navigator.back()
                }

                CreateProjectComponent.UiAction.SaveSuccess -> {
                    echo("Successfully saved project")
                    navigator.back()
                }

                null -> {
                    // do nothing
                }
            }
        }
    }

    private val mappedFeelings = buildMap {
        component.state.value.allFeelings.forEachIndexed { index, feeling ->
            put(key = index.toString(), value = feeling)
        }
    }
    private val feelingsList =
        mappedFeelings.toList().joinToString("\n") { "${it.first} - ${it.second}" }

    private val projectName: String by option()
        .prompt("What is the name of your project?")
    private val projectEffort: UInt by option()
        .convert { it.toUIntOrNull() ?: 0u }
        .prompt("What is the effort for this project? (1-10)")
    private val projectNotes: String by option()
        .prompt("Notes about the project", default = "")
    private val projectFeelings: List<Feeling> by option()
        .convert {
            val indexes = it.split(" ").mapNotNull { it.toUIntOrNull() }
            mappedFeelings.mapNotNull {
                if (it.key.toUInt() in indexes) it.value else null
            }
        }
        .prompt("$feelingsList\nHow are you feeling? (Choose all of the numbers that apply separated by a space)")
    private val projectFeelingsNotes: String by option()
        .prompt("Notes about your current feelings", default = "")

    override suspend fun suspendRun() {
        echo(
            """
            
            Creating Project:
             name: $projectName
             effort: $projectEffort
             notes: $projectNotes
             feelings: ${projectFeelings.joinToString(", ")}
             feelings notes: $projectFeelingsNotes
        """.trimIndent()
        )

        component.updateProjectName(projectName)
        component.updateNotes(projectNotes)
        component.updateFeelingsNotes(projectFeelingsNotes)
        component.updateData {
            copy(
                feelings = projectFeelings,
                effort = projectEffort.toFloat(),
            )
        }

        component.save()
    }
}
