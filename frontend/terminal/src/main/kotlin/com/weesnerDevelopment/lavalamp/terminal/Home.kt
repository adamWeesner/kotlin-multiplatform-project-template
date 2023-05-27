package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent

class Home(
    private val component: HomeComponent,
    private val onOptionSelected: (StartingOption) -> Unit
) : SuspendingCliktCommand() {
    sealed class StartingOption(
        val name: String
    ) {
        object CreateProject : StartingOption(name = "Create project")
        object ViewProject : StartingOption(name = "View project")
        object Exit : StartingOption(name = "Exit")
    }

    private val choices = buildMap<String, StartingOption> {
        var currentIndex = 0

        put(currentIndex.toString(), StartingOption.CreateProject).also { currentIndex++ }

        component.getAllProjects()

        if (component.state.value.projects.isNotEmpty())
            put(currentIndex.toString(), StartingOption.ViewProject).also { currentIndex++ }

        put(currentIndex.toString(), StartingOption.Exit).also { currentIndex++ }
    }

    private val choicesList = choices.toList()
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val action: StartingOption by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nWhat would you like to do?")

    override suspend fun suspendRun() {
        onOptionSelected(action)
    }
}
