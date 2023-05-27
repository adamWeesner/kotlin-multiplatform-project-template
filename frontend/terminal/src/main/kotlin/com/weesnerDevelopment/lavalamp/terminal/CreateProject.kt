package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.weesnerDevelopment.lavalamp.sdk.Feeling
import com.weesnerDevelopment.lavalamp.sdk.Project
import com.weesnerDevelopment.lavalamp.sdk.State
import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProjectComponent
import java.util.*

class CreateProject(
    private val component: CreateProjectComponent,
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

        component.save(project)
    }
}
