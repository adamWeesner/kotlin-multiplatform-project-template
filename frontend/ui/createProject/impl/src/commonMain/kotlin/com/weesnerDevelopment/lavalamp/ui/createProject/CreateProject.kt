package com.weesnerDevelopment.lavalamp.ui.createProject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.weesnerDevelopment.compose.core.LocalWindowSize
import com.weesnerDevelopment.compose.core.components.ClearIconData
import com.weesnerDevelopment.compose.core.components.Input
import com.weesnerDevelopment.lavalamp.frontend.resources.strings.Strings
import com.weesnerDevelopment.navigation.Navigator

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CreateProject(
    component: CreateProjectComponent,
    navigator: Navigator
) {
    val state by component.state.subscribeAsState()

    val windowSize = LocalWindowSize.current
    val feelingItemHeight = 32.dp
    val feelingsRows = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 4
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 2
        else -> 4
    }

    when (state.uiAction) {
        CreateProjectComponent.UiAction.SaveFailure -> {
            component.uiAction(null)
            // todo show errors and allow to fix
        }

        CreateProjectComponent.UiAction.SaveSuccess -> {
            component.uiAction(null)
            navigator.back()
        }

        null -> {
            // do nothing
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Input(
            modifier = Modifier.padding(top = 8.dp),
            label = Strings.ProjectNameLabel,
            input = state.projectName,
            onValueChange = { component.updateProjectName(it) },
            trailingIcon = ClearIconData(
                enabled = state.projectName.value.isNotBlank(),
                onClick = { component.updateProjectName("") }
            ),
        )

        Input(
            modifier = Modifier.padding(top = 8.dp),
            label = Strings.NotesLabel,
            maxLines = 5,
            input = state.notes,
            onValueChange = { component.updateNotes(it) },
            trailingIcon = ClearIconData(
                enabled = state.notes.value.isNotBlank(),
                onClick = { component.updateNotes("") }
            ),
        )

        LazyHorizontalStaggeredGrid(
            modifier = Modifier
                .height((feelingsRows * feelingItemHeight.value).dp)
                .padding(vertical = 8.dp),
            rows = StaggeredGridCells.Fixed(feelingsRows),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalItemSpacing = 4.dp,
            userScrollEnabled = true
        ) {
            items(state.allFeelings) { feeling ->
                FilterChip(
                    selected = state.feelings.contains(feeling),
                    selectedIcon = {
                        Image(Icons.Default.Check, null)
                    },
                    content = {
                        Text(
                            text = Strings.FeelingLabel(feeling),
                            maxLines = 1,
                            overflow = TextOverflow.Visible
                        )
                    },
                    onClick = {
                        val selectedFeelings = if (state.feelings.contains(feeling)) {
                            state.feelings.filterNot { it == feeling }
                        } else {
                            state.feelings + feeling
                        }
                        component.updateData { copy(feelings = selectedFeelings) }
                    },
                )
            }
        }

        Input(
            modifier = Modifier.padding(top = 8.dp),
            label = Strings.FeelingsNotesLabel,
            maxLines = 5,
            input = state.feelingsNotes,
            onValueChange = { component.updateFeelingsNotes(it) },
            trailingIcon = ClearIconData(
                enabled = state.feelingsNotes.value.isNotBlank(),
                onClick = { component.updateFeelingsNotes("") }
            ),
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = Strings.EffortLabel
        )

        Slider(
            modifier = Modifier.padding(horizontal = 8.dp),
            value = state.effort,
            onValueChange = {
                component.updateData { copy(effort = it) }
            },
            valueRange = 0f.rangeTo(10f),
            steps = 9,
        )

        Button(
            modifier = Modifier.padding(top = 8.dp),
            enabled = state.saveEnabled,
            onClick = {
                component.save()
            }
        ) {
            Text(
                text = Strings.Save
            )
        }
    }
}
