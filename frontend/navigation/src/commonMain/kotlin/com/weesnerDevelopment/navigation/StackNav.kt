package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.weesnerDevelopment.common.Platform

class StackNav<T : Config, C: Child>(
    val navigator: StackNavigation<T>,
    val stack: Value<ChildStack<T, C>>
)

inline fun <reified T : Config, reified C: Child> ComponentContext.setupStackNav(
    platform: Platform,
    initial: List<T>,
    noinline factory: (T, ComponentContext) -> C
): StackNav<T, C> {
    val navigator: StackNavigation<T> = when (platform) {
        Platform.Terminal -> CustomStackNavigation()
        Platform.Desktop, Platform.Android -> StackNavigation()
    }

    val childStack = childStack<T, C>(
        source = navigator,
        initialStack = { initial },
        handleBackButton = true,
        childFactory = factory
    )

    return StackNav(navigator, childStack)
}
