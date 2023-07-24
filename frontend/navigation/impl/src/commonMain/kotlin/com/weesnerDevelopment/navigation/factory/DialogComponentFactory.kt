package com.weesnerDevelopment.navigation.factory

import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.DialogConfig
import org.kodein.di.DI
import org.kodein.di.DIAware

internal interface DialogComponentFactory {
    fun create(config: DialogConfig): Child.Dialog
}

internal class DefaultDialogComponentFactory(
    override val di: DI
) : DialogComponentFactory, DIAware {
    override fun create(config: DialogConfig): Child.Dialog {
        return when (config) {
            is Config.SampleForDialog -> {
                Child.SampleForDialog
            }
        }
    }
}
