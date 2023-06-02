package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.Platform
import com.weesnerDevelopment.navigation.factory.BottomSheetComponentFactory
import com.weesnerDevelopment.navigation.factory.DialogComponentFactory
import com.weesnerDevelopment.navigation.factory.DrawerComponentFactory
import com.weesnerDevelopment.navigation.factory.ScreenComponentFactory

interface RootComponent {
    val screenStack: Value<ChildStack<ScreenConfig, Child.Screen>>
    val dialogSlot: Value<ChildSlot<DialogConfig, Child.Dialog>>
    val bottomSheetSlot: Value<ChildSlot<BottomSheetConfig, Child.BottomSheet>>
    val drawerSlot: Value<ChildSlot<DrawerConfig, Child.Drawer>>
}

interface Navigator {
    fun bringToFront(config: Config)
    fun back()
}

internal class DefaultRootComponent(
    componentContext: ComponentContext,
    platform: Platform,
    private val screenFactory: ScreenComponentFactory,
    private val dialogFactory: DialogComponentFactory,
    private val drawerFactory: DrawerComponentFactory,
    private val bottomSheetFactory: BottomSheetComponentFactory,
) : RootComponent, ComponentContext by componentContext, Navigator {
    private val configsOnStack: MutableList<Config> = mutableListOf(Config.Home)

    private val screenNav: StackNav<ScreenConfig, Child.Screen> = setupStackNav(
        platform = platform,
        initial = listOf(Config.Home),
        factory = { config, _ ->
            screenFactory.create(config, this)
        }
    )

    override val screenStack: Value<ChildStack<ScreenConfig, Child.Screen>> = screenNav.stack

    private val dialogSlotNav: SlotNav<DialogConfig, Child.Dialog> = setupSlotNav(
        key = "DialogSlot",
        platform = platform,
        factory = { config, _ ->
            dialogFactory.create(config, this)
        }
    )

    override val dialogSlot: Value<ChildSlot<DialogConfig, Child.Dialog>> = dialogSlotNav.slot

    private val drawerSlotNav: SlotNav<DrawerConfig, Child.Drawer> = setupSlotNav(
        key = "DrawerSlot",
        platform = platform,
        factory = { config, _ ->
            drawerFactory.create(config, this)
        }
    )

    override val drawerSlot: Value<ChildSlot<DrawerConfig, Child.Drawer>> = drawerSlotNav.slot

    private val bottomSheetSlotNav: SlotNav<BottomSheetConfig, Child.BottomSheet> =
        setupSlotNav(
            key = "BottomSheetSlot",
            platform = platform,
            factory = { config, _ ->
                bottomSheetFactory.create(config, this)
            }
        )

    override val bottomSheetSlot: Value<ChildSlot<BottomSheetConfig, Child.BottomSheet>> =
        bottomSheetSlotNav.slot

    /**
     * Clears slot stacks when navigating between screens
     */
    override fun bringToFront(config: Config) {
        configsOnStack.add(config)

        when (config) {
            is ScreenConfig -> {
                screenNav.navigator.bringToFront(config)
                dialogSlotNav.navigator.dismiss()
                bottomSheetSlotNav.navigator.dismiss()
                drawerSlotNav.navigator.dismiss()
            }

            is DialogConfig -> {
                dialogSlotNav.navigator.activate(config)
            }

            is DrawerConfig -> {
                drawerSlotNav.navigator.activate(config)
            }

            is BottomSheetConfig -> {
                bottomSheetSlotNav.navigator.activate(config)
            }
        }
    }

    /**
     * Clears slot stacks when popping screens
     */
    override fun back() {
        when (configsOnStack.lastOrNull()) {
            is ScreenConfig -> {
                screenNav.navigator.pop()
                dialogSlotNav.navigator.dismiss()
                bottomSheetSlotNav.navigator.dismiss()
                drawerSlotNav.navigator.dismiss()
            }

            is DialogConfig -> {
                dialogSlotNav.navigator.dismiss()
            }

            is DrawerConfig -> {
                drawerSlotNav.navigator.dismiss()
            }

            is BottomSheetConfig -> {
                bottomSheetSlotNav.navigator.dismiss()
            }

            null -> throw IllegalArgumentException("We should not get here, how is this possible")
        }

        configsOnStack.removeLast()
    }
}
