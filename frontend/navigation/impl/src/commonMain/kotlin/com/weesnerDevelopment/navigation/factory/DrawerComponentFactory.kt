package com.weesnerDevelopment.navigation.factory

import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.DrawerConfig
import org.kodein.di.DI
import org.kodein.di.DIAware

internal interface DrawerComponentFactory {
    fun create(config: DrawerConfig): Child.Drawer
}

internal class DefaultDrawerComponentFactory(
    override val di: DI
) : DrawerComponentFactory, DIAware {
    override fun create(config: DrawerConfig): Child.Drawer {
        return when (config) {
            is Config.SampleForDrawer -> {
                Child.SampleForDrawer
            }
        }
    }
}
