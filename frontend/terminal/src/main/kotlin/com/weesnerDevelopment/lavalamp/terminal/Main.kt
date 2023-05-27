package com.weesnerDevelopment.lavalamp.terminal

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.ajalt.clikt.core.CliktCommand
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.InMemoryProjectRepository
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.DefaultRootComponent
import com.weesnerDevelopment.navigation.RootComponent
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess

internal val onlyDispatcher = Dispatchers.Unconfined

fun main(args: Array<String>) {
    Program().main(args)
}

class Program : CliktCommand() {
    private val lifecycle = LifecycleRegistry()
    private val componentContext = DefaultComponentContext(lifecycle = lifecycle)

    private val projectRepo = InMemoryProjectRepository

    private val rootComponent = DefaultRootComponent(
        platform = Platform.Terminal,
        projectRepository = projectRepo,
        componentContext = componentContext,
        coroutineContext = onlyDispatcher,
        navContext = onlyDispatcher,
    )

    override fun run() {
        RootContent(
            programArgs = currentContext.originalArgv,
            projectRepo = projectRepo,
            component = rootComponent
        )
    }
}

fun RootContent(
    programArgs: List<String>,
    projectRepo: ProjectRepository,
    component: RootComponent
) {
    component.stack.subscribe {
        val command = when (val current = it.active.instance) {
            is Child.Home -> Home(
                component = current.component,
                onOptionSelected = { option ->
                    when (option) {
                        Home.StartingOption.CreateProject -> {
                            current.component.onCreateProject()
                        }

                        Home.StartingOption.ViewProject -> {
                            PickProject(
                                projectRepository = projectRepo,
                                onProjectSelected = { projectId ->
                                    current.component.onProjectDetails(projectId)
                                }
                            ).main(programArgs)
                        }

                        Home.StartingOption.Exit -> {
                            exitProcess(0)
                        }
                    }
                }
            )

            is Child.CreateProject -> CreateProject(component = current.component)
            is Child.ProjectDetails -> ViewProject(component = current.component)
        }

        command.main(programArgs)
    }
}
