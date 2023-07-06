package com.weesnerDevelopment.navigation.factory

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.DialogConfig
import com.weesnerDevelopment.navigation.Navigator
import kotlin.coroutines.CoroutineContext

internal interface DialogComponentFactory {
    fun create(config: DialogConfig, navigator: Navigator): Child.Dialog
}

internal class DefaultDialogComponentFactory(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
) : DialogComponentFactory {
    override fun create(config: DialogConfig, navigator: Navigator): Child.Dialog {
        return when (config) {
            is Config.SampleForDialog -> {
                Child.SampleForDialog
            }
        }
    }
}
