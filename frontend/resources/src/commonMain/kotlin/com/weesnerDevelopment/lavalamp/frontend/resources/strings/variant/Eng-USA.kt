package com.weesnerDevelopment.lavalamp.frontend.resources.strings.variant

import com.weesnerDevelopment.lavalamp.frontend.resources.strings.AllStrings
import com.weesnerDevelopment.lavalamp.sdk.Feeling

internal object `Eng-USA` : AllStrings {
    override val AppName: String = "lavalamp"
    override val ProjectNameLabel: String = "Project Name"
    override val NotesLabel: String = "Notes"
    override val FeelingsNotesLabel: String = "Notes on your feelings"
    override val EffortLabel: String = "Effort"
    override val Save: String = "Save"
    override val BlankProjectName: String = "Project name cannot be blank"
    override val GetStartedMessage: String = "Create a project to get started."
    override val CreateProject: String = "Create Project"

    override fun InvalidProjectName(name: String): String = "Invalid project name '$name'"
    override fun FeelingLabel(feeling: Feeling): String = feeling.name
}
