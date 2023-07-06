package com.weesnerDevelopment.navigation.factory

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.DrawerConfig
import com.weesnerDevelopment.navigation.Navigator
import kotlin.coroutines.CoroutineContext

internal interface DrawerComponentFactory {
    fun create(config: DrawerConfig, navigator: Navigator): Child.Drawer
}

internal class DefaultDrawerComponentFactory(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
) : DrawerComponentFactory {
    override fun create(config: DrawerConfig, navigator: Navigator): Child.Drawer {
        return when (config) {
            is Config.SampleForDrawer -> {
                Child.SampleForDrawer
            }
        }
    }
}
