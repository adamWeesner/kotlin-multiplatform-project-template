package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlin.coroutines.CoroutineContext

internal fun createChildDrawer(
    config: DrawerConfig,
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    navContext: CoroutineContext,
    projectRepository: ProjectRepository,
    screenNav: StackNav<ScreenConfig, Child.Screen>,
    dialogNav: SlotNav<DialogConfig, Child.Dialog>,
    bottomSheetNav: SlotNav<BottomSheetConfig, Child.BottomSheet>,
): Child.Drawer = when (config) {
    is Config.SampleForDrawer -> {
        Child.SampleForDrawer
    }
}
