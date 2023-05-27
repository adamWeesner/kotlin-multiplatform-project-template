package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import com.weesnerDevelopment.lavalamp.sdk.Project
import kotlinx.coroutines.runBlocking

class PickProject(
    private val projectRepository: ProjectRepository,
    private val onProjectSelected: (projectId: String) -> Unit,
) : CliktCommand() {
    private val choices: Map<String, Project>

    init {
        choices = runBlocking(onlyDispatcher) {
            val list = mutableMapOf<String, Project>()

            projectRepository.getAll()
                .onSuccess {
                    it.forEachIndexed { index, project ->
                        list[index.toString()] = project
                    }
                }

            list
        }
    }

    private val choicesList = choices.toList()
        .sortedBy { it.first }
        .joinToString("\n") { "${it.first} - ${it.second.name}" }

    private val selectedProject: Project by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nWhich project do you want to view?")

    override fun run() {
        echo("Opening project...")
        onProjectSelected(selectedProject.id)
    }
}
