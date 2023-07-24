package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.di.setupDI
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.RootComponent
import kimchi.Kimchi
import kimchi.logger.StandardWriter
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

internal val onlyDispatcher = Dispatchers.Unconfined

fun main(args: Array<String>) = Program().main(args)

class Program : CliktCommand(), DIAware {
    private val showLogs = false

    override val di: DI by DI.lazy {
        extend(setupDI(Platform.Terminal))
    }

    private val rootComponent by instance<RootComponent>()
    private val projectRepo by instance<ProjectRepository>()

    override fun run() {
        if (showLogs)
            Kimchi.addLog(StandardWriter)

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
                navigator = component,
                pickProject = {
                    PickProject(
                        projectRepository = projectRepo,
                        onProjectSelected = { projectId ->
                            component.bringToFront(Config.ProjectDetails(projectId))
                        }
                    ).main(programArgs)
                },
            )

            is Child.CreateProject -> CreateProject(
                component = current.component,
                navigator = component,
            )

            is Child.ProjectDetails -> ViewProject(
                component = current.component,
                navigator = component
            )
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
