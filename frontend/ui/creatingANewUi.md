# Creating a new UI

Creating a new UI element, a `Screen`, `Dialog` or anything else you want to show a user, tries to
be as simple as possible. There are a few things you need to do to wire this all up. We
use [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for our UIs
whenever we can. There are a few spots your new UI can live:

## Where to add new reusable Composables

If you're adding a new composable that is meant to be a reusable "core" thing add it to
[compose/core](frontend/compose/core/src/commonMain/kotlin/com/weesnerDevelopment/compose/core)

If you're adding a new Ui element that is meant to be navigated to add it to a respective Gradle
module in [ui](frontend/ui). Each UI element like a screen should have its own Gradle module.

Each each UI element that contains state or action should have its
own [Component](wiringUpANewUi.md#component) in its respective Gradle module.

## Terminal Frontend

We have a terminal frontend for quick iteration on new concepts and to say we can run on a terminal
too ;) The terminal frontend uses [Clikt](https://github.com/ajalt/clikt). To create
our `FancyBanner` in the terminal it could look something like this:

```kotlin
package com.weesnerDevelopment.lavalamp.terminal

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.choice

class FancyBanner(
    component: FancyBannerComponent
) : CliktCommand() {
    private val choices: Map<String, String> = mapOf("0" to "yes", "1" to "no")

    private val choicesList = choices.toList()
        .joinToString("\n") { "${it.first} - ${it.second}" }

    private val selection: String by option()
        .choice(choices = choices)
        .prompt("${choicesList}\nDo you want to try again?")

    override fun run() {
        when (selection) {
            choices[0] -> {
                echo("trying again...")
                component.apiCallOnAction()
            }
            choices[1] -> {
                echo("not trying again")
            }
            else -> {
                // this should never happen, Clikt will catch actions not in the `choices` list
            }
        }
    }
}

```