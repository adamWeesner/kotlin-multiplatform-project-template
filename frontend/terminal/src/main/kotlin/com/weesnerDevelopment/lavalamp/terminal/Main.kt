package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice
import com.weesnerDevelopment.lavalamp.api.InMemoryProjectRepository
import com.weesnerDevelopment.lavalamp.api.ProjectRepository
import com.weesnerDevelopment.lavalamp.api.ProjectRepositoryError
import com.weesnerDevelopment.lavalamp.sdk.Feeling
import com.weesnerDevelopment.lavalamp.sdk.Project
import com.weesnerDevelopment.lavalamp.sdk.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.exitProcess

abstract class SuspendingCliktCommand(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CliktCommand() {
    /**
     * use `suspendRun` to get coroutine scope handling for free
     */
    override fun run() {
        runBlocking(dispatcher) {
            suspendRun()
        }
    }

    abstract suspend fun suspendRun()
}

fun main(args: Array<String>) = Program().main(args)

class Program : CliktCommand() {
    val projectRepo = InMemoryProjectRepository

    override fun run() {
        Home(projectRepo).main(currentContext.originalArgv)
    }
}

class Home(
    private val projectRepository: ProjectRepository
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

        runBlocking {
            if (projectRepository.getAll().isSuccessful)
                put(currentIndex.toString(), StartingOption.ViewProject).also { currentIndex++ }
        }

        put(currentIndex.toString(), StartingOption.Exit).also { currentIndex++ }
    }

    private val choicesList = choices.toList()
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val action: StartingOption by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nWhat would you like to do?")

    override suspend fun suspendRun() {
        when (action) {
            StartingOption.CreateProject -> {
                CreateProject(projectRepository).main(currentContext.originalArgv)
            }

            StartingOption.ViewProject -> {
                PickProject(projectRepository).main(currentContext.originalArgv)
            }

            StartingOption.Exit -> {
                exitProcess(0)
            }
        }
    }
}

class CreateProject(
    private val projectRepository: ProjectRepository
) : SuspendingCliktCommand() {
    private val mappedFeelings = buildMap {
        Feeling.values().forEachIndexed { index, feeling ->
            put(key = index.toString(), value = feeling)
        }
    }
    private val feelingsList =
        mappedFeelings.toList().joinToString("\n") { "${it.first} - ${it.second}" }

    private val name: String by option()
        .prompt("What is the name of your project?")
    private val effort: UInt by option()
        .convert { it.toUIntOrNull() ?: 0u }
        .prompt("What is the effort for this project? (1-10)")
    private val notes: String by option()
        .prompt("Notes about the project", default = "")
    private val feelings: List<Feeling> by option()
        .convert {
            val indexes = it.split(" ").mapNotNull { it.toUIntOrNull() }
            mappedFeelings.mapNotNull {
                if (it.key.toUInt() in indexes) it.value else null
            }
        }
        .prompt("$feelingsList\nHow are you feeling? (Choose all of the numbers that apply separated by a space)")
    private val feelingsNotes: String by option()
        .prompt("Notes about your current feelings", default = "")

    override suspend fun suspendRun() {
        echo(
            """
            
            Creating Project:
             name: $name
             effort: $effort
             notes: $notes
             feelings: ${feelings.joinToString(", ")}
             feelings notes: $feelingsNotes
        """.trimIndent()
        )

        val date = Date().toString()
        val project = Project(
            id = UUID.randomUUID().toString(),
            name = name,
            notes = notes,
            state = State.Started(
                feelings = feelings,
                notes = feelingsNotes,
                date = date,
            ),
            effort = effort,
            rewards = emptyList(),
            dueDate = null,
            goalDate = null,
            createdDate = date,
            lastUpdatedDate = date
        )

        projectRepository.add(project) {
            onSuccess {
                echo("Project created\n")
                Home(projectRepository).main(currentContext.originalArgv)
            }
            onFailure {
                echo("An error occurred attempting to save project:\n $it")
            }
        }
    }
}

class PickProject(
    private val projectRepository: ProjectRepository
) : SuspendingCliktCommand() {
    private val choices = runBlocking {
        val list = mutableMapOf<String, Project>()

        projectRepository.getAll()
            .onSuccess {
                it.forEachIndexed { index, project ->
                    list[index.toString()] = project
                }
            }

        list
    }

    private val choicesList = choices.toList()
        .sortedBy { it.first }
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val selectedProject: Project by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nWhich project do you want to view?")

    override suspend fun suspendRun() {
        echo("Opening project...")
        ViewProject(projectRepository, selectedProject).main(currentContext.originalArgv)
    }
}

class ViewProject(
    private val projectRepository: ProjectRepository,
    private val project: Project,
) : SuspendingCliktCommand() {
    sealed class Option(
        val name: String
    ) {
        object DeleteProject : Option(name = "Delete project")
        object Back : Option(name = "Back to home")
    }

    val details = with(project) {
        """
            Project:
             name: $name
             effort: $effort
             notes: $notes
             feelings: ${state.feelings.joinToString(", ")}
             feelings notes: ${state.notes}
             
        """.trimIndent()
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
                projectRepository.delete(project.id) {
                    onSuccess {
                        echo("Successfully deleted project")
                        Home(projectRepository).main(currentContext.originalArgv)
                    }
                    onFailure {
                        echo("An error occurred attempting to save project:\n $it")
                        when (it) {
                            ProjectRepositoryError.Delete.ActionFailed -> TODO()
                            ProjectRepositoryError.Delete.ProjectNotFound -> TODO()
                        }
                    }
                }
            }

            Option.Back -> {
                Home(projectRepository).main(currentContext.originalArgv)
            }
        }
    }
}
