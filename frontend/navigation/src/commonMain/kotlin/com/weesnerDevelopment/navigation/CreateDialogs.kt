package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlin.coroutines.CoroutineContext

internal fun createChildDialog(
    config: DialogConfig,
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    navContext: CoroutineContext,
    projectRepository: ProjectRepository,
    screenNav: StackNav<ScreenConfig, Child.Screen>,
    bottomSheetNav: SlotNav<BottomSheetConfig, Child.BottomSheet>,
    drawerNav: SlotNav<DrawerConfig, Child.Drawer>,
): Child.Dialog = when (config) {
    is Config.SampleForDialog -> {
        Child.SampleForDialog
    }
}
