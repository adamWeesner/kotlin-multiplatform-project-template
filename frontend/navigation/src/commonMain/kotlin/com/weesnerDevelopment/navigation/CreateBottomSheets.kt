package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlin.coroutines.CoroutineContext

internal fun createChildBottomSheet(
    config: BottomSheetConfig,
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    navContext: CoroutineContext,
    projectRepository: ProjectRepository,
    screenNav: StackNav<ScreenConfig, Child.Screen>,
    dialogNav: SlotNav<DialogConfig, Child.Dialog>,
    drawerNav: SlotNav<DrawerConfig, Child.Drawer>,
): Child.BottomSheet = when (config) {
    is Config.SampleForBottomSheet -> {
        Child.SampleForBottomSheet
    }
}
