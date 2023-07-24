package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice
import com.weesnerDevelopment.lavalamp.ui.home.HomeComponent
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

class Home(
    private val component: HomeComponent,
    private val navigator: Navigator,
    private val pickProject: () -> Unit,
) : CliktCommand() {
    sealed class StartingOption(
        val name: String
    ) {
        object CreateProject : StartingOption(name = "Create project")
        object ViewProject : StartingOption(name = "View project")
        object Exit : StartingOption(name = "Exit")
    }

    init {
        component.state.subscribe { state ->
            when (val action = state.uiAction) {
                HomeComponent.Navigate.CreateProject -> {
                    navigator.bringToFront(Config.CreateProject)
                }

                is HomeComponent.Navigate.ProjectDetails -> {
                    navigator.bringToFront(Config.ProjectDetails(action.id))
                }

                null -> {
                    // do nothing
                }
            }
        }
    }

    private val choices = buildMap<String, StartingOption> {
        var currentIndex = 0

        put(currentIndex.toString(), StartingOption.CreateProject).also { currentIndex++ }

        runBlocking {
            component.getAllProjects()
        }

        if (component.state.value.projects.isNotEmpty())
            put(currentIndex.toString(), StartingOption.ViewProject).also { currentIndex++ }

        put(currentIndex.toString(), StartingOption.Exit).also { currentIndex++ }
    }

    private val choicesList = choices.toList()
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val action: StartingOption by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nWhat would you like to do?")

    override fun run() {
        when (action) {
            StartingOption.CreateProject -> component.uiAction(HomeComponent.Navigate.CreateProject)
            StartingOption.Exit -> exitProcess(0)
            StartingOption.ViewProject -> pickProject()
        }
    }
}
