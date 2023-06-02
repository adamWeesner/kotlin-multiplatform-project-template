package com.weesnerDevelopment.navigation

import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.scoped
import org.kodein.di.singleton

actual val componentContextModule by DI.Module {
    bind<ComponentContext> {
        scoped(WeakContextScope.of<AppCompatActivity>()).singleton {
            with(context) {
                defaultComponentContext()
            }
        }
    }
}
