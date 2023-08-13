package com.weesnerDevelopment.lavalamp.frontend.resources.strings

import com.weesnerDevelopment.lavalamp.sdk.Feeling

/**
 * Core interface for all strings in the app. You will need to implement the strings for each
 * language we support manually. To use the strings simply run `./gradlew generateStrings` from
 * there a [Strings.kt] file will be automatically generated for you and you can use your new
 * strings like this `Strings.AppName` or `Strings.MyStringWithVariables(variable)`
 */
interface AllStrings {
    val AppName: String
    val ProjectNameLabel: String
    val NotesLabel: String
    val FeelingsNotesLabel: String
    val EffortLabel: String
    val Save: String
    val BlankProjectName: String
    val GetStartedMessage: String
    val CreateProject: String

    fun InvalidProjectName(name: String): String
    fun FeelingLabel(feeling: Feeling): String
}
