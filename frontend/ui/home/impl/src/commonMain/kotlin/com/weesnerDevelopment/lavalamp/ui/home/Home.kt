package com.weesnerDevelopment.lavalamp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.weesnerDevelopment.compose.core.LocalWindowSize
import com.weesnerDevelopment.lavalamp.frontend.resources.strings.Strings
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator

@Composable
fun Home(
    component: HomeComponent,
    navigator: Navigator,
) {
    val windowSize = LocalWindowSize.current
    val projectCardWidth = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 400.dp
        WindowWidthSizeClass.Medium -> 600.dp
        WindowWidthSizeClass.Expanded -> 800.dp
        else -> 400.dp
    }

    LaunchedEffect("GetAllProjects") {
        component.getAllProjects()
    }

    val state by component.state.subscribeAsState()

    when (val action = state.uiAction) {
        HomeComponent.Navigate.CreateProject -> {
            component.uiAction(null)
            navigator.bringToFront(Config.CreateProject)
        }

        is HomeComponent.Navigate.ProjectDetails -> {
            component.uiAction(null)
            navigator.bringToFront(Config.ProjectDetails(action.id))
        }

        null -> {
            // do nothing
        }
    }

    // todo make the screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.projects.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = Strings.GetStartedMessage
                )

                Button(
                    onClick = { component.uiAction(HomeComponent.Navigate.CreateProject) }
                ) {
                    Text(
                        text = Strings.CreateProject
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(.1f)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = { component.uiAction(HomeComponent.Navigate.CreateProject) }
                ) {
                    Text(
                        text = Strings.CreateProject
                    )
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.projects) { project ->
                    Card(
                        modifier = Modifier.width(projectCardWidth),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator.bringToFront(Config.ProjectDetails(project.id))
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                style = MaterialTheme.typography.bodyLarge,
                                text = project.name
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                text = project.notes
                            )

                            project.dueDate?.let { dueDate ->
                                Text(
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = MaterialTheme.typography.bodySmall,
                                    text = dueDate
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
