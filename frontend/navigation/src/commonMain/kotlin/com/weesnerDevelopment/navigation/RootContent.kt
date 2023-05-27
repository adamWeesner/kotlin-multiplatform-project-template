package com.weesnerDevelopment.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProject
import com.weesnerDevelopment.lavalamp.ui.home.Home
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetails

@Composable
fun RootContent(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier
) {
    val stack by rootComponent.stack.subscribeAsState()

    Surface(color = MaterialTheme.colors.background, modifier = modifier) {
        Children(
            stack = stack,
            animation = stackAnimation()
        ) { child ->
            when (val current = child.instance) {
                is Child.Home -> Home(current.component)
                is Child.CreateProject -> CreateProject(current.component)
                is Child.ProjectDetails -> ProjectDetails(current.component)
            }
        }
    }
}