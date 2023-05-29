package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.Platform

class SlotNav<T : Config, C : Child>(
    val navigator: SlotNavigation<T>,
    val slot: Value<ChildSlot<T, C>>
)

inline fun <reified T : Config, reified C : Child> ComponentContext.setupSlotNav(
    platform: Platform,
    initial: T? = null,
    noinline factory: (T, ComponentContext) -> C
): SlotNav<T, C> {
    val navigator: SlotNavigation<T> = when (platform) {
        Platform.Terminal -> CustomSlotNavigation()
        Platform.Desktop, Platform.Android -> SlotNavigation()
    }

    val childStack = childSlot<T, C>(
        source = navigator,
        initialConfiguration = { initial },
        handleBackButton = true,
        childFactory = factory
    )

    return SlotNav(navigator, childStack)
}
