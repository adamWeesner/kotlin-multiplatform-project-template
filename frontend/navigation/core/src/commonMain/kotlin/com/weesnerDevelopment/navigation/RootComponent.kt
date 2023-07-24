package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent : Navigator {
    val screenStack: Value<ChildStack<ScreenConfig, Child.Screen>>
    val dialogSlot: Value<ChildSlot<DialogConfig, Child.Dialog>>
    val bottomSheetSlot: Value<ChildSlot<BottomSheetConfig, Child.BottomSheet>>
    val drawerSlot: Value<ChildSlot<DrawerConfig, Child.Drawer>>
}

interface Navigator {
    fun bringToFront(config: Config)
    fun back()
}
