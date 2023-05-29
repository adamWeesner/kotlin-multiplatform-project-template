package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.lavalamp.api.project.ProjectRepository
import kotlin.coroutines.CoroutineContext

interface RootComponent {
    val screenStack: Value<ChildStack<ScreenConfig, Child.Screen>>
    val dialogSlot: Value<ChildSlot<DialogConfig, Child.Dialog>>
    val bottomSheetSlot: Value<ChildSlot<BottomSheetConfig, Child.BottomSheet>>
    val drawerSlot: Value<ChildSlot<DrawerConfig, Child.Drawer>>
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    platform: Platform,
    private val projectRepository: ProjectRepository,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
) : RootComponent, ComponentContext by componentContext {
    private val screenNav: StackNav<ScreenConfig, Child.Screen> = setupStackNav(
        platform = platform,
        initial = listOf(Config.Home),
        factory = { config, context ->
            createChildScreen(
                config = config,
                componentContext = context,
                coroutineContext = coroutineContext,
                navContext = navContext,
                projectRepository = projectRepository,
                dialogNav = dialogSlotNav,
                bottomSheetNav = bottomSheetSlotNav,
                drawerNav = drawerSlotNav,
                bringToFront = ::bringToFront,
                back = ::back
            )
        }
    )

    override val screenStack: Value<ChildStack<ScreenConfig, Child.Screen>> = screenNav.stack

    private val dialogSlotNav: SlotNav<DialogConfig, Child.Dialog> = setupSlotNav(
        platform = platform,
        factory = { config, context ->
            createChildDialog(
                config = config,
                componentContext = context,
                coroutineContext = coroutineContext,
                navContext = navContext,
                projectRepository = projectRepository,
                screenNav = screenNav,
                bottomSheetNav = bottomSheetSlotNav,
                drawerNav = drawerSlotNav,
            )
        }
    )

    override val dialogSlot: Value<ChildSlot<DialogConfig, Child.Dialog>> = dialogSlotNav.slot

    private val bottomSheetSlotNav: SlotNav<BottomSheetConfig, Child.BottomSheet> = setupSlotNav(
        platform = platform,
        factory = { config, context ->
            createChildBottomSheet(
                config = config,
                componentContext = context,
                coroutineContext = coroutineContext,
                navContext = navContext,
                projectRepository = projectRepository,
                screenNav = screenNav,
                dialogNav = dialogSlotNav,
                drawerNav = drawerSlotNav,
            )
        }
    )

    override val bottomSheetSlot: Value<ChildSlot<BottomSheetConfig, Child.BottomSheet>> =
        bottomSheetSlotNav.slot

    private val drawerSlotNav: SlotNav<DrawerConfig, Child.Drawer> = setupSlotNav(
        platform = platform,
        factory = { config, context ->
            createChildDrawer(
                config = config,
                componentContext = context,
                coroutineContext = coroutineContext,
                navContext = navContext,
                projectRepository = projectRepository,
                screenNav = screenNav,
                dialogNav = dialogSlotNav,
                bottomSheetNav = bottomSheetSlotNav,
            )
        }
    )

    override val drawerSlot: Value<ChildSlot<DrawerConfig, Child.Drawer>> = drawerSlotNav.slot

    /**
     * Clears slot stacks when navigating between screens
     */
    private fun bringToFront(item: ScreenConfig) {
        screenNav.navigator.bringToFront(item)
        dialogSlotNav.navigator.dismiss()
        bottomSheetSlotNav.navigator.dismiss()
        drawerSlotNav.navigator.dismiss()
    }

    /**
     * Clears slot stacks when popping screens
     */
    private fun back() {
        screenNav.navigator.pop()
        dialogSlotNav.navigator.dismiss()
        bottomSheetSlotNav.navigator.dismiss()
        drawerSlotNav.navigator.dismiss()
    }
}
