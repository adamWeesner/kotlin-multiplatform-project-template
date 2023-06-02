@file:JvmName("DiJvm")

package com.weesnerDevelopment.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.kodein.di.DI
import org.kodein.di.bindSingleton

actual val componentContextModule by DI.Module {
    bindSingleton<ComponentContext> {
        DefaultComponentContext(LifecycleRegistry())
    }
}
