package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.RootComponent
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import kotlin.system.exitProcess

internal val onlyDispatcher = Dispatchers.Unconfined

fun main(args: Array<String>) = Program().main(args)

class Program : CliktCommand(), DIAware {
    override val di: DI by DI.lazy {
        extend(setupDI(Platform.Terminal))
    }

    private val rootComponent by instance<RootComponent>()
    private val projectRepo by instance<ProjectRepository>()

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
) = with(component) {
    screenStack.subscribe {
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

    dialogSlot.subscribe {
        val command = when (val current = it.child?.instance) {
            Child.SampleForDialog -> object : CliktCommand() {
                override fun run() {}
            }

            null -> null
        }

        command?.main(programArgs)
    }

    bottomSheetSlot.subscribe {
        val command = when (val current = it.child?.instance) {
            Child.SampleForBottomSheet -> object : CliktCommand() {
                override fun run() {}
            }

            null -> null
        }

        command?.main(programArgs)
    }

    drawerSlot.subscribe {
        val command = when (val current = it.child?.instance) {
            Child.SampleForDrawer -> object : CliktCommand() {
                override fun run() {}
            }

            null -> null
        }

        command?.main(programArgs)
    }
}
