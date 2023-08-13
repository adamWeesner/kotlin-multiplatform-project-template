package com.weesnerDevelopment.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.weesnerDevelopment.lavalamp.frontend.resources.strings.Strings
import com.weesnerDevelopment.lavalamp.ui.createProject.CreateProject
import com.weesnerDevelopment.lavalamp.ui.home.Home
import com.weesnerDevelopment.lavalamp.ui.projectDetails.ProjectDetails

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootContent(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier
) {
    val screenStack by rootComponent.screenStack.subscribeAsState()
    val dialogSlot by rootComponent.dialogSlot.subscribeAsState()
    val drawerSlot by rootComponent.drawerSlot.subscribeAsState()
    val bottomSheetSlot by rootComponent.bottomSheetSlot.subscribeAsState()

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = rememberBottomSheetScaffoldState(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Strings.AppName
                    )
                }
            )
        },
        sheetPeekHeight = when (bottomSheetSlot.child?.instance) {
            Child.SampleForBottomSheet -> BottomSheetScaffoldDefaults.SheetPeekHeight
            null -> 0.dp
        },
        sheetContent = {
            when (bottomSheetSlot.child?.instance) {
                Child.SampleForBottomSheet -> TODO()
                null -> {} /* left blank intentionally, we dont wanna do anything if we dont have a thing in there */
            }
        },
        drawerContent = when (drawerSlot.child?.instance) {
            Child.SampleForDrawer -> {
                { TODO() }
            }

            null -> null
        },
    ) {
        when (dialogSlot.child?.instance) {
            Child.SampleForDialog -> TODO()
            null -> {} /* left blank intentionally, we dont wanna do anything if we dont have a thing in there */
        }

        Children(
            modifier = Modifier.fillMaxSize(),
            stack = screenStack,
            animation = stackAnimation()
        ) { child ->
            when (val current = child.instance) {
                is Child.Home -> Home(current.component, rootComponent)
                is Child.CreateProject -> CreateProject(current.component, rootComponent)
                is Child.ProjectDetails -> ProjectDetails(current.component, rootComponent)
            }
        }
    }
}
